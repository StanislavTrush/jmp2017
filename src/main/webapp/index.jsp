<html>
    <head>
        <title>Form</title>
    </head>
    <body>
        <details>
            <summary>Execute available actions</summary>
            <form action="process" method="post">
       			<p>
       			    <b>Enter input data:</b>
       			</p>
                   <p>
                       <textarea name="data" rows="10" cols="45"></textarea>
                   </p>
       			<p>
       			    <button type="submit">Execute available actions</button>
       			</p>
       		</form>
        </details>

        <details>
            <summary>Show available by entered data actions</summary>
            <form action="availableActions" method="post">
                <p>
                    <b>Enter input data:</b>
                </p>
                <p>
                    <textarea name="data" rows="10" cols="45"></textarea>
                </p>
                <p>
                    <button type="submit">Show available actions</button>
                </p>
            </form>
        </details>
    </body>
</html>