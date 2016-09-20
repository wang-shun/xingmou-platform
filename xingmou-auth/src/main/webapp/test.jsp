<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>  
            <head>  
            <title>File Input</title>  
            <meta http-equiv="content-type" content="text/html; charset=utf-8">  
            <style>  
            bodyfont-size:13px;  
            .boxborder:1px solid #eee;background:#ffe;padding:10px 30px;margin:10px;  
            dtfont-weight:bold;  
            dt,img,textareamargin:8px 0;  
            emcolor:#f00; font-style:normal;  
            spancolor:#999;  
            .ftcolor:#999;font-size:11px;text-align:right  
            </style>  
            </head>  
            <body>  
            <h2>在不同浏览器中获取File Input的value</h2>  
            <div class="box">  
            <input type="file" name="foo" id="foo" size="60" />  
            <input type="button" value="Show Value" onclick="alert(document.getElementById('foo').value)" />  
            <input type="button" value="Show Value in FF3" onclick="alert(getValueFF('foo'))" />  
            <input type="button" value="Show Value in IE8" onclick="alert(getValueIE8('foo'))" />  
            </div>  
            <div class="box">  
            <dl>  
            <dt>Firefox 2.x、IE6、IE7</dt>  
            <dd>在本地及远端都可以取得完整地址（包括路径及文件名）</dd>  
            </dl>  
            <dl>  
            <dt>Firefox 3.0</dt>  
            <dd>在本地及远端都<em>不能</em>取得完整地址，只能获得文件名</dd>  
            </dl>  
            <dl>  
            <dt>IE8、IE7[IE8模拟]</dt>  
            <dd>在本地可以取得完整地址（包括路径及文件名），在远端<em>不能</em>取得完整地址，只能获得文件名</dd>  
            </dl>  
            <span>* 本地：localhost</span>  
            </div>  
            <div class="box">  
            <h3>如何在Firefox3下取得完整路径</h3>  
            <p>  
            第一步：打开"about:config"页面，查找"signed.applets.codebase_principal_support"属性，将其值设置为true。  
            </p>  
            <p>  
            第二步：在javascript中采用以下代码进行获取：<br />  
            <textarea style="width:800px;height:300px;">  
            function getValueFF(id)  
            var ip = document.getElementById(id);  
            if (ip.files)   
            //ffx3 - try to have access to full path  
            try   
            netscape.security.PrivilegeManager.enablePrivilege( 'UniversalFileRead' )  
              
            catch (err)   
            //need to set signed.applets.codebase_principal_support to true  
              
            ;  
            return ip.value;  
              
            </textarea>  
            </p>  
            <p>  
            经过以上步骤，在Firefox3下获取file input的value时，会弹出一个Internet Security的对话框，点击"Allow"将可以获取完整value。<br />  
            <span>资料：<a href="https://bugzilla.mozilla.org/show_bug.cgi?id=143220" target="_blank">https://bugzilla.mozilla.org/show_bug.cgi?id=143220</a></span>  
            </p>  
            </div>  
            <div class="box">  
            <h3>如何在IE8下取得完整路径</h3>  
            <p>方法一：使用selection.createRange <br />  
            <textarea style="width:800px;height:150px;">  
            function getValueIE8(id)  
            var ip = document.getElementById(id);  
            ip.select();  
            return document.selection.createRange().text;  
              
            </textarea>  
            </p>  
            <p>方法二：引用：<a href="https://bugzilla.mozilla.org/attachment.cgi?id=328849" target="_blank">https://bugzilla.mozilla.org/attachment.cgi?id=328849</a></p>  
            </div>  
            <script language="JavaScript">  
            function getValueFF(id)  
            var ip = document.getElementById(id);  
            if (ip.files)   
            //ffx3 - try to have access to full path  
            try   
            netscape.security.PrivilegeManager.enablePrivilege( 'UniversalFileRead' )  
              
            catch (err)   
            //need to set signed.applets.codebase_principal_support to true  
              
            ;  
            return ip.value;  
              
            function getValueIE8(id)  
            var ip = document.getElementById(id);  
            ip.select();  
            return document.selection.createRange().text;  
              
            </script>  
            </body>  
            </html>