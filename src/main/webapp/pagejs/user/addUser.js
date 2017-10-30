var $;
layui.config({
	base : "js/"  //你存放新模块的目录，注意，不是layui的模块目录
}).use(['form','layer','jquery'],function(){
	var form = layui.form(),
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage;
		$ = layui.jquery;

 	var addUserArray = [],addUser;
 	form.on("submit(addUser)",function(data){
 		addUser =  '{"username":"'+ $(".username").val() +'",';  //登录名
 		addUser += '"password":"'+ $(".password").val() +'",';	 //邮箱
 		addUser += '"email":"'+ $(".email").val() +'",'; //性别
 		addUser += '"mobile":"'+ $(".mobile").val() +'"}'; //会员等级
 		var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            url:'/user/add',
            type:'POST', //GET
            //contentType: "application/json; charset=utf-8",
            async:true,    //或false,是否异步
            data:JSON.parse(addUser),
            dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
            beforeSend:function(xhr){
                console.log(xhr)
                console.log('发送前')
            },
            success:function(data,textStatus,jqXHR){
                console.log(data)
                console.log(textStatus)

				if (data.statusCode == 200){
                    top.layer.msg("用户添加成功！");
				}else{
                    top.layer.msg("用户添加失败！");
				}

            },
            error:function(xhr,textStatus){
                console.log('错误')
                //console.log(xhr)
                console.log(textStatus)
            },
            complete:function(){
                console.log('结束');
                top.layer.close(index);
                layer.closeAll("iframe");
                //刷新父页面
                parent.location.reload();
            }
        })
 		return false;
 	});

    $("#username").blur(function(){
        $.get("/user/exists?userName=" + $("#username").val(), function (data) {
            if (data){
                $("#checkUserName").html("用户名已被占用");
            }
        });

    });
});

//格式化时间
function formatTime(_time){
    var year = _time.getFullYear();
    var month = _time.getMonth()+1<10 ? "0"+(_time.getMonth()+1) : _time.getMonth()+1;
    var day = _time.getDate()<10 ? "0"+_time.getDate() : _time.getDate();
    var hour = _time.getHours()<10 ? "0"+_time.getHours() : _time.getHours();
    var minute = _time.getMinutes()<10 ? "0"+_time.getMinutes() : _time.getMinutes();
    return year+"-"+month+"-"+day+" "+hour+":"+minute;
}
