<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${not empty conditions}">
    <c:forEach var="condition" items="${conditions}">
        <c:set var="id" value="${id + 1}" scope="session"/>
        <ul class="media-list">
            <li class="media">
                <div class="media-left" style="min-width:100px">
                    <form action="remove" method="post" style="float:left">
                        <input type="hidden" name="conditionId" value="${condition.id}"/>
                        <button type="submit" class="btn btn-default">
                            <span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
                        </button>
                    </form>
                    <button type="button" onclick="toggleById('addForm${id}')" class="btn btn-default">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                    </button>
                </div>
                <div class="media-body">
                    ${condition.attribute} ${condition.operation} ${condition.value} ${condition.className}
                    <ul id="addForm${id}" class="media-list" style="display:none">
                        <li class="media">
                            <div class="media-left">

                            </div>
                            <div class="media-body">
                                <form action="add" method="post">
                                    <button type="submit" class="btn btn-default">
                                        <span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span>
                                    </button>
                                    <input type="text" name="operation" placeholder="operation"/>
                                    <input type="text" name="attribute" placeholder="attribute"/>
                                    <input type="text" name="valueAttr" placeholder="value"/>
                                    <input type="text" name="className" placeholder="className"/>
                                    <input type="hidden" name="parentId" value="${condition.id}"/>
                                    <input type="hidden" name="actionId" value="0"/>
                                </form>
                            </div>
                        </li>
                    </ul>
                    <c:if test="${not empty condition.conditions}">
                        <c:set var="conditions" value="${condition.conditions}" scope="request"/>
                        <jsp:include page="node.jsp"/>
                    </c:if>
                </div>
            </li>
        </ul>
    </c:forEach>
</c:if>