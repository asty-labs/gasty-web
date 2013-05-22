<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>crewman - ${domain.description}</title>
    <script src="${resource(dir: 'js', file: 'jquery-1.8.3.js')}" type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'jquery-ui-1.9.2.custom.js')}" type="text/javascript"></script>
    <script src="${m.versionedResource(dir: 'js', file: 'jquery.form.js')}" type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'jquery.ui.touch-punch.js')}" type="text/javascript"></script>
    <script src="${m.versionedResource(dir: 'js', file: 'jasty-core.js')}" type="text/javascript"></script>
    <script src="${m.versionedResource(dir: 'js', file: 'jasty-components.js')}" type="text/javascript"></script>
    <script src="${m.versionedResource(dir: 'js', file: 'jasty-mobile.js')}" type="text/javascript"></script>
    <link rel="stylesheet" href="${resource(dir: 'css/ui-darkness', file: 'jquery-ui-1.9.2.custom.min.css')}"/>
    <link rel="stylesheet" href="${m.versionedResource(dir: 'css', file: 'crewman.css')}"/>
    <link href="${resource(dir: 'css', file: 'mrc1880.png')}" rel="shortcut icon" type="image/x-icon" />
    <link rel="apple-touch-icon" href="${resource(dir: 'css', file: 'mrc1880.png')}"/>
    <g:if test="${params["m"] != null}">
        <meta name="apple-mobile-web-app-capable" content="yes" />
        <meta name="viewport" content="width=device-width,initial-scale=${scale},minimum-scale=${scale},maximum-scale=${scale}">
        <script type="text/javascript">
            initializeMobile();
        </script>
    </g:if>
</head>
<body class="${params["m"] != null ? 'mobile':''} domain-${domain.code} ${osType ? 'os-' + osType:''} browser-${browser}">
<script type="text/javascript">
    jasty.settings.formEngineUrl = "${g.createLink([controller: 'app', action: 'doAction']).replaceAll("/app/", "/" + domain.code + "/app/")}";
</script>
<div class="MainContainer">
    <div class="CenteredContainer">
              <m:formViewer id="test" entryPoint="crewman.MainForm" parameters="${domain.code}"/>
    </div>
    <div class="jasty-blocker"></div>
</div>
</body>
</html>