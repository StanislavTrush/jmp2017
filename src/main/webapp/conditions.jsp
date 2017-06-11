<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Conditions</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
      function toggle (elements, specifiedDisplay) {
        var element, index;

        elements = elements.length ? elements : [elements];
        for (index = 0; index < elements.length; index++) {
          element = elements[index];

          if (isElementHidden(element)) {
            element.style.display = '';

            // If the element is still hidden after removing the inline display
            if (isElementHidden(element)) {
              element.style.display = specifiedDisplay || 'block';
            }
          } else {
            element.style.display = 'none';
          }
        }
        function isElementHidden (element) {
          return window.getComputedStyle(element, null).getPropertyValue('display') === 'none';
        }
      }
      function toggleById(id) {
        toggle(document.getElementById(id));
      }
    </script>
</head>
<body>
    <c:if test="${not empty isDeleted}">
        <c:choose>
            <c:when test="${isDeleted}">
                <div class="alert alert-success alert-dismissible" role="alert">
                  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  Item deleted.
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-danger alert-dismissible" role="alert">
                  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  Error during deletion.
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>
    <c:if test="${not empty isCreated}">
            <c:choose>
                <c:when test="${isCreated}">
                    <div class="alert alert-success alert-dismissible" role="alert">
                      <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                      Item added.
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-danger alert-dismissible" role="alert">
                      <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                      Error occurred while trying to add item.
                    </div>
                </c:otherwise>
            </c:choose>
        </c:if>
    <div style="padding-left:50px">
        <h1>List of conditions:</h1>
        <c:set var="id" value="0" scope="session"/>
        <jsp:include page="node.jsp"/>
    </div>
</body>
</html>