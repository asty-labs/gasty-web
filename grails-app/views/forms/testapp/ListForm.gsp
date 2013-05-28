<%@ page contentType="text/html;charset=UTF-8" %>
<style>
.list a {
    display: block;
}
</style>

<h1>Item list</h1>
<hr/>
<jasty_std:textBox id="searchText" />
<jasty_std:button onClick="searchClicked" text="Search" />
<hr/>
<div class="list" style="border: 1px solid black;width: 400px; height: 100px;overflow: auto;">
</div>
<jasty_std:button onClick="addClicked" text="Add..." />




