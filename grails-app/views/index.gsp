<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Form samples</title>
    <script src="${jasty.resource(dir: 'js', file: 'jquery-1.8.3.js')}" type="text/javascript"></script>
    <script src="${jasty.resource(dir: 'js', file: 'jquery.form.js')}" type="text/javascript"></script>
    <script src="${jasty.resource(dir: 'js', file: 'jasty-core.js')}" type="text/javascript"></script>
    <script src="${jasty.resource(dir: 'js', file: 'jasty-std.js')}" type="text/javascript"></script>
</head>
<body>
<script>
    jasty.settings.formEngineUrl = "${g.createLink([controller: 'gasty', action: 'doAction'])}";
</script>
<g:if test="${params.entry}">
    <jasty:formViewer id="myform" entryPoint="${params.entry}" />
</g:if>
<g:else>
    <a href="?entry=guessnumber.MainForm">Guess number demo</a>
    <br/>
    <a href="?entry=testapp.ListForm">Another demo</a>
</g:else>

</body>
</html>
