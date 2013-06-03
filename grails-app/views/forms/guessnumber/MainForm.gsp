<h1>Guess a number</h1>
<div id="${currentForm.id}_stats">
    <g:include view="/forms/guessnumber/MainForm_stats.gsp" model="[currentForm: currentForm]"/>
</div>
<jasty_std:textBox id="guessEntryField" />
<jasty_std:button onClick="processGuess" text="Submit Your Guess" />
<jasty_std:button onClick="startNewGame" text="Start a New Game" />




