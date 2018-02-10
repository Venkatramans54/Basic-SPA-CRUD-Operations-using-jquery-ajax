<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Books Store Application</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">


$(document).ready(function() {
           $.get('book',function(responseJson) {    
            if(responseJson!=null){
                $("#countrytable").find("tr:gt(0)").remove();
                var table1 = $("#countrytable");
                $.each(responseJson, function(key,value) { 
                     var rowNew = $("<tr><td></td><td></td><td></td><td></td><td></td><td></td></tr>");
                        rowNew.children().eq(0).text(value['id']); 
                        rowNew.children().eq(1).text(value['title']); 
                        rowNew.children().eq(2).text(value['author']); 
                        rowNew.children().eq(3).text(value['price']); 
                     //var value=
                        rowNew.children().eq(4).append("<button onclick='update("+value['id']+")'>edit</button>");
                       // rowNew.children().eq(5).append('<button id="edit" aedit="'+value['id']+'" >edit</button>');
                        rowNew.children().eq(5).append('<a href="/book/delete?code='+value['id']+'" >delete</a>');
                        rowNew.appendTo(table1);
                    
});


            $("#tablediv").show(); 
// edit
    }         
    });
$('#update1').click(function(evt) {
    
        var id=$("#idid").val();
        var title=$("#idtitle").val();
        var author=$("#idauthor").val();        
        var price=$("#idprice").val();

        $.get('/book/update',{id:id,title:title,author:author,price:price},function(responseJson) {

         });
 
});                
  });    
function update(data) {
   
       // var id=$("#edit").val();
        
        $.get('/book/edit',{value:data},function(responseJson) {
        
                //alert(responseJson);
                $.each(responseJson, function(idx, obj) {	
                    $('#idid').val(obj.id);         
                  $('#idtitle').val(obj.title);
                  $('#idauthor').val(obj.author);
                 $('#idprice').val(obj.price);
         });
 
});                
            }

</script>
</head>
<body>
    <center>
        <h1>Books Management</h1>        
    </center>
    <div align="center">
          <form action="#">  
        <table border="1" cellpadding="5">  
        <tr>
                <th>ID: </th>
                <td>
                    <input type="text" id="idid" name="title" size="45"/>
                </td>
            </tr>      
            <tr>
                <th>Title: </th>
                <td>
                    <input type="text" id="idtitle" name="title" size="45"/>
                </td>
            </tr>
            <tr>
                <th>Author: </th>
                <td>
                    <input type="text" id="idauthor"  name="author" size="45" />
                </td>
            </tr>
            <tr>
                <th>Price: </th>
                <td>
                    <input type="text" id="idprice"  name="price" size="5"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" id="update1" value="Save" />
                </td>
            </tr>
        </table>
        </form>
    </div>

    <div id="tablediv">
<table cellspacing="0" id="countrytable"> 
    <tr> 
        <th scope="col">id</th> 
        <th scope="col">title</th> 
        <th scope="col">author</th>          
        <th scope="col">price</th>          
    </tr> 
</table>
</div>   
</body>
</html>