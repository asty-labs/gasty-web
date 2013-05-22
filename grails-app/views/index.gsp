<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Form samples</title>
    <script src="${resource(dir: 'js', file: 'jquery-1.8.3.js')}" type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'jquery.form.js')}" type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'jasty-core.js')}" type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'jasty-components.js')}" type="text/javascript"></script>
</head>
<body>
<script>
    jasty.settings.formEngineUrl = "${g.createLink([controller: 'app', action: 'doAction'])}";
</script>
<m:formViewer id="myform" entryPoint="testapp.ListForm" />
</body>
</html>
