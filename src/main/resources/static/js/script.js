console.log("this is script file");

const toggle=()=>{
if(  $(".sidebar").is(":visible"))
{  
    $(".sidebar").css("display","none") ;  
       $(".content").css("margin-left","0px") ; 
}
else
{
	 $(".sidebar").css("display","block") ; 
	  $(".content").css("margin-left","18%") ;
}	
}
