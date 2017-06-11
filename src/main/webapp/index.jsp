<html>
    <head>
        <title>Form</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>
        <ul class="nav nav-tabs">
            <li role="presentation" class="active">
                <a data-toggle="tab" href="#actions1">Execute available actions</a>
            </li>
            <li role="presentation">
                <a data-toggle="tab" href="#actions2">Show available by entered data actions</a>
            </li>
        </ul>
        <div class="tab-content">
            <div id="actions1" class="tab-pane fade in active">
                <form action="process" method="post">
                    <h3>Enter input data:</h3>
                    <textarea class="form-control" name="data" rows="10" cols="45"></textarea>
                    <button type="submit" class="btn btn-default">Execute available actions</button>
                </form>
            </div>
            <div id="actions2" class="tab-pane fade">
                <form action="availableActions" method="post">
                    <h3>Enter input data:</h3>
                    <textarea class="form-control" name="data" rows="10" cols="45"></textarea>
                    <button type="submit" class="btn btn-default">Show available actions</button>
                </form>
            </div>
        </div>
    </body>
</html>