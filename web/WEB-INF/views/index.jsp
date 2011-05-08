<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head><title>Jenkins/Arduino status</title></head>
  <body>

  <h1>Choose mode</h1>
  <form action="changemode" method="post">
    <p>Production sets the arduinolamp to show status from Jenkins.</p>
    <p>Test makes arduino run some led tests. Test is default when the app is started</p>
    <p>Note that the arduino could be in another state than the buttons indicate. Ie it was restarted etc.<br/>
        The only way to be sure is to watch the leds.
    </p>

    <input id="test" name="mode" type="radio" title="Test" value="test"/>
    <label for="test" title="Test">Test</label>

    <br/>
    <input id="production" name="mode" type="radio" title="Production" value="production"/>
    <label for="production" title="Production">Production</label>
    <br/>
    <input type="submit" value="Set mode" />

  </form>


  </body>
</html>