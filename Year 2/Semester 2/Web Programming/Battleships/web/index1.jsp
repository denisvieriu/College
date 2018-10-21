<%@ page language="java" %>
<%
    if (session.isNew()) {
        int[] squareValues = {-1, -1, -1, -1, -1, -1, -1, -1, -1};
        session.setAttribute("squareValues", squareValues);
    }
    int[] squareValues = null;
    for (int i = 0; i < 9; i++) {
        squareValues = (int[]) session.getAttribute("squareValues");
        String s = request.getParameter("square" + i);
        if ((s != null) && s.toLowerCase().equals("x")) {
            squareValues[i] = 1;
        }
        if ((s != null) && s.toLowerCase().equals("o")) {
            squareValues[i] = 0;
        }
        session.setAttribute("squareValues", squareValues);
    }
%>

<%!
    private int getChoice(int squareNo, int[] squareValues) {
        return squareValues[squareNo];
    }
%>
<html>
<head>
    <title>Tic Tac Toe</title>
</head>
<body bgcolor="#FFFFFF">
<h1>A basic version of Tic-tac-toe (Noughts and Crosses)</h1>
<form action="tictactoe.jsp">
    <table>
        <% /* Establish a loop for 9 squares in 3 rows of 3 */
            for (int counter=0; counter < 9; counter++) {
                /* A row every three squares */
                if (counter % 3 == 0) { %>
        <tr><td width="15">
                <% } else { %>
            <td width="15">
                <% }
                    /* Each cell should have an O, an X or an input field */
                    switch (getChoice(counter, squareValues)) {
                        case 0: %>
                O
                <% break;
                    case 1: %>
                X
                <% break;
                    default: %>
                <input type="text" size="1" length="1" name="square<%= counter %>" />
                <% break;
                }
                    /* Finish row every three squares */
                    if ((counter + 1) % 3 == 0) { %>
            </td></tr>
        <% } else { %>
        </td>
        <% }
        } %>


    </table>
    <input type="submit" value="Confirm Choice" />
</form>
</body>
</html>