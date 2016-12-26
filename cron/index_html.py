#!/usr/bin/python
# -*- coding: utf-8 -*-
import calendar
import json

import datetime
import redis
import time, MySQLdb
import sys

reload(sys)
sys.setdefaultencoding("utf-8")


red = redis.Redis(host='10.9.210.22', port=6386, db=1,password="xhhl-redis")

conn=MySQLdb.connect(host="10.9.130.142",user="xmuser",passwd="qcDKywE7Ka52",db="edw2",charset="utf8")
conn_doc=MySQLdb.connect(host="10.9.130.142",user="xmuser",passwd="qcDKywE7Ka52",db="xm_doc",charset="utf8")
cursor = conn.cursor()
cursor_doc = conn_doc.cursor()
def week_get(d):
    dayscount = datetime.timedelta(days=d.isoweekday())
    dayto = d - dayscount
    sixdays = datetime.timedelta(days=6)
    dayfrom = dayto - sixdays
    date_from = datetime.datetime(dayfrom.year, dayfrom.month, dayfrom.day, 0, 0, 0)
    date_to = datetime.datetime(dayto.year, dayto.month, dayto.day, 23, 59, 59)
    return [str(date_from), str(date_to)]


def data_total():
    n = cursor.execute("select count(id) total from dm_project")
    project_total = cursor.fetchall()[0][0]

    n = cursor.execute("select count(id) total from dm_invest_events")
    invest_events_total = cursor.fetchall()[0][0]

    n = cursor.execute("select count(id) total from dm_investfirms")
    investfirm_total = cursor.fetchall()[0][0]

    n = cursor.execute("select count(id) total from dm_investor")
    investor_total = cursor.fetchall()[0][0]

    data = {"project":project_total,"invest_events":invest_events_total,"investfirm":investfirm_total,"investor":investor_total}
    s = json.dumps(data)
    red.set("index:data_total",s)


def top_invest():
    td = datetime.date.today()
    n = cursor.execute("select "
                       "ie.project_id ,ie.invest_money,ie.invest_date,ie.round_name,ie.industry_name,"
                       "ie.investfirm_names,ie.add_time,p.title,p.pic_xm "
                       "from dm_invest_events ie "
                       "right join "
                       "dm_project p "
                       "on p.id = ie.project_id "
                       "order by "
                       "ie.id "
                       "desc "
                       "limit 8")

    datas = []
    for row in cursor.fetchall():
        data = {}
        data["project_id"] = row[0]
        data["invest_money"] = row[1]
        invest_date  = data["invest_date"] = row[2]
        d = (td-invest_date).days
        if (d == 0):
            data["invest_date"] = str(d) +"刚刚"
        elif (d <=4):
            data["invest_date"] = str(d) +"天前"
        else:
            data["invest_date"] = row[2].strftime("%Y-%m-%d")
        data["round_name"] = row[3]
        data["industry_name"] = row[4]
        data["investfirm_names"] = row[5]
        data["add_time"] = row[6].strftime("%Y-%m-%d %H:%M:%S")
        data["title"] = row[7]
        data["pic"] = row[8]
        if data['pic'] == None or data["pic"] == "":
            data['pic'] = "project/default.png"
        datas.append(data)
    s = json.dumps(datas)
    red.set("index:topinvest",s)


def top_project():

    n = cursor.execute("select "
                       " p.id, p.title ,p.district_name,p.district_sub_name,p.pic_xm,p.description, "
                       "p.newest_event_round ,pp.name as p_name, pp.postion_name  from dm_project p "
                       "right join dm_project_person pp on pp.project_id = p.id  "
                        " group by p.id "
                       "order by p.id desc "
                       "limit 4;")

    datas = []
    project_ids = []
    for row in cursor.fetchall():
        data = {}
        data["id"] = row[0]
        data["title"] = row[1]
        data["district_name"] = row[2]
        data["district_sub_name"] = row[3]
        data["pic_xm"] = row[4]
        if data['pic_xm'] == None or data["pic_xm"] == "":
            data['pic_xm'] = "project/default.png"
        data["description"] = row[5]
        data["newest_event_round"] = row[6]
        data["p_name"] = row[7]
        data["postion_name"] = row[8]
        #data["tag"] = row[9]

        datas.append(data)
    for data in datas:
        tags = []
        cursor.execute("select title from dm_label where type =1 and relation_id = "+str(data["id"])+"")
        #cursor.execute("select title from dm_label where type =1 and relation_id = 16488")
        for row in cursor.fetchall():
            tags.append(row[0])
        data["tags"] = tags

    s = json.dumps(datas)
    #print str
    red.set("index:topproject",s)

def top_investfirm():
    n = cursor.execute("select i.id , i.name ,count(i.id) total from dm_investfirms i right join dm_invest_events_investfirm iei   on iei.investfirm_id = i.id  group by iei.investfirm_id order by total desc limit 10")
    datas = []
    for row in cursor.fetchall():
        data = {}
        data["id"] = row[0]
        data["name"] = row[1]
        data["total"] = row[2]

        datas.append(data)
    s = json.dumps(datas)
    #print str
    red.set("index:topinvestfirm",s)

def last_weekinvestfirm():

    week = week_get(datetime.date.today())
    n = cursor.execute("select "
                       "i.id , i.name ,count(i.id) total from dm_investfirms i "
                       "right join dm_invest_events_investfirm iei   on iei.investfirm_id = i.id   "
                       "left join dm_invest_events ie on iei.event_id = ie.id  "
                       "where invest_date > %s and invest_date < %s group by i.id order by total desc limit 10;",(week[0],week[1]))
    datas = []
    for row in cursor.fetchall():
        data = {}
        data["id"] = row[0]
        data["name"] = row[1]
        data["total"] = row[2]

        datas.append(data)
    s = json.dumps(datas)
    red.set("index:lastweekinvestfirm",s)

def doc():
    n = cursor_doc.execute("select "
                       "id , title ,publish_date,icon,summary_short,source_type "
                       "from  "
                       "dm_industry_reports "
                       "order by  order_num desc  "
                       "limit 5")
    datas = []
    for row in cursor_doc.fetchall():
        data = {}
        data["id"] = row[0]
        data["title"] = row[1]
        data["publish_date"] = row[2].strftime("%Y-%m-%d")
        data["icon"] = row[3]
        data["summary_short"] = row[4]
        data["source_type"] = row[5]

        if data["source_type"] == 1:
            data["source_type"] = "易观"
        elif data["source_type"] == 2:
            data["source_type"] = "艾瑞"

        datas.append(data)
    s = json.dumps(datas)
    red.set("index:topdoc",s)

if __name__ == '__main__':
    data_total()
    top_invest()
    top_project()
    top_investfirm()
    last_weekinvestfirm()
    doc()