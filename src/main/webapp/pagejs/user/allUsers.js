layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'laypage'], function () {
    var form = layui.form(),
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery;

    //分页
    var nums = 6; //每页出现的数据量
    //加载页面数据
    var usersData = '';

    //获取数据
    function getDataList(currentPage,keyWord) {
        var index = layer.msg('查询中，请稍候', {icon: 16, time: false, shade: 0.8});
        if (!currentPage) {
            currentPage = 1;
        }
        $.get("/user/list?currentPage=" + currentPage + "&pageSize=" + nums, function (data) {
            layer.close(index);
            if (data.statusCode == 200) {
                usersData = data.data.datas;
                //执行加载数据的方法
                usersList(data.data.allCount);
            } else {
                layer.msg("数据异常");
            }
        });

    }

    //带查询条件的分页
    function getDataListByKeyWord(currentPage,keyWord){
        if (!currentPage) {
            currentPage = 1;
        }
        $.get("/user/listkeyword?currentPage=" + currentPage + "&pageSize=" + nums + "&keyWord=" + keyWord, function (data) {
            if (data.statusCode == 200) {
                usersData = data.data.datas;
                console.log(data.data.allCount);
                console.log(usersData);
                /*if (window.sessionStorage.getItem("addUser")) {
                    var addUsers = window.sessionStorage.getItem("addUser");
                    usersData = JSON.parse(addUsers).concat(usersData);
                }*/
                //执行加载数据的方法
                usersList(data.data.allCount);
            } else {
                layer.msg("数据异常");
            }

        });
    }

    //获取数据
    getDataList();

    //是否展示
    form.on('switch(isDelete)', function (data) {
        var index = layer.msg('修改中，请稍候', {icon: 16, time: false, shade: 0.8});
        console.log(data.elem.checked);
        console.log(data.value);
        var u_status = data.elem.checked ? 1 : 0;
        var u_id = $(data.elem).attr("data-id");
        $.get("/user/updateStatus?id=" + u_id + "&status=" + u_status, function (data) {
            layer.close(index);
            if (data.statusCode == 200) {
                layer.msg("状态修改成功！");
            } else {
                layer.msg("状态修改失败！");
            }
        });
    })

    //查询
    $(".search_btn").click(function () {
        var userArray = [];
        if ($(".search_input").val() != '') {
            var index = layer.msg('查询中，请稍候', {icon: 16, time: false, shade: 0.8});
            setTimeout(function(){
                console.log("xxx")
            },5000)
            getDataListByKeyWord(1,$(".search_input").val());
            layer.close(index);
        } else {
            layer.msg("请输入需要查询的内容");
        }
    });

    //添加用户
    $(".usersAdd_btn").click(function () {
        var index = layui.layer.open({
            title: "添加用户",
            type: 2,
            content: "/page/user/addUser",
            success: function (layero, index) {
                layui.layer.tips('点击此处返回文章列表', '.layui-layer-setwin .layui-layer-close', {
                    tips: 3
                });
            }
        });
        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        $(window).resize(function () {
            layui.layer.full(index);
        })
        layui.layer.full(index);
    });

    //批量删除
    $(".batchDel").click(function () {
        var chk_value =[];
        $('input[name="checked"]:checked').each(function(){
            chk_value.push($(this).attr("id"));
        });
        if (chk_value.length > 0){
            layer.confirm('确定删除选中记录吗？', {icon: 3, title: '提示信息'}, function (index) {
                var index = layer.msg('删除中，请稍候',{icon: 16,time:false,shade:0.8});

                $.get("/user/deletes?ids=" + chk_value, function (data) {
                    layer.close(index);
                    if (data.statusCode == 200) {
                        layer.msg("删除成功");
                        getDataList();
                        usersList(usersData);

                    } else {
                        layer.msg("删除失败");
                    }
                });
            });
        }else{
            layer.msg("请选中要删除的记录! ");
        }
        console.log(chk_value);
    });

    //刷新
    $(".batchRefresh").click(function (){
        getDataList();
    });

    //全选
    form.on('checkbox(allChoose)', function (data) {
        var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"])');
        child.each(function (index, item) {
            item.checked = data.elem.checked;
        });
        form.render('checkbox');
    });

    //通过判断文章是否全部选中来确定全选按钮是否选中
    form.on("checkbox(choose)", function (data) {
        var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"])');
        var childChecked = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"]):checked')
        if (childChecked.length == child.length) {
            $(data.elem).parents('table').find('thead input#allChoose').get(0).checked = true;
        } else {
            $(data.elem).parents('table').find('thead input#allChoose').get(0).checked = false;
        }
        form.render('checkbox');
    });

    //操作
    //编辑
    $("body").on("click", ".users_edit", function () {  //编辑
        layer.alert('您点击了会员编辑按钮，由于是纯静态页面，所以暂时不存在编辑内容，后期会添加，敬请谅解。。。', {icon: 6, title: '文章编辑'});
    });

    //删除
    $("body").on("click", ".users_del", function () {  //删除
        var _this = $(this);
        layer.confirm('确定删除此用户？', {icon: 3, title: '提示信息'}, function (index) {
            //_this.parents("tr").remove();
            layer.close(index);
            $.get("/user/delete?id=" + _this.attr("data-id"), function (data) {
                if (data.statusCode == 200) {
                    layer.msg("删除成功");
                    getDataList();
                    usersList(usersData);

                } else {

                }
            });


        });
    });

    //渲染数据
    function usersList(allCount) {
        //渲染数据
        function renderDate(data, curr) {
            var dataHtml = '';
            currData = '';
            currData = usersData;//.concat().splice(curr * nums - nums, nums);
            if (currData.length != 0) {
                var c_status = '';
                for (var i = 0; i < currData.length; i++) {
                    if (currData[i].status) {
                        c_status = "checked";
                    }

                    dataHtml += '<tr>'
                        + '<td><input type="checkbox" name="checked" lay-skin="primary" lay-filter="choose" id="'+  currData[i].id +'"></td>'
                        + '<td>' + currData[i].username + '</td>'
                        + '<td>' + currData[i].email + '</td>'
                        + '<td>' + currData[i].mobile + '</td>'
                        + '<td>' + currData[i].regTime + '</td>'
                        + '<td>' + currData[i].regIp + '</td>'
                        + '<td>' + currData[i].lastLoginTime + '</td>'
                        + '<td>' + currData[i].lastLoginIp + '</td>'
                        + '<td>' + currData[i].updateTime + '</td>'
                        //+ '<td>' + currData[i].status + '</td>'
                        + '<td><input type="checkbox" name="status" lay-skin="switch" lay-text="是|否" lay-filter="isDelete"' + c_status + ' data-id="' + currData[i].id + '"></td>'
                        + '<td>'
                        + '<a class="layui-btn layui-btn-mini users_edit"><i class="iconfont icon-edit"></i> 编辑</a>'
                        + '<a class="layui-btn layui-btn-danger layui-btn-mini users_del" data-id="' + currData[i].id + '"><i class="layui-icon">&#xe640;</i> 删除</a>'
                        + '</td>'
                        + '</tr>';
                    c_status = '';
                }
            } else {
                dataHtml = '<tr><td colspan="11">暂无数据</td></tr>';
            }
            return dataHtml;
        }


        laypage({
            cont: "page",
            pages: Math.ceil(allCount / nums),
            jump: function (obj,first) {
                console.log(obj.curr);
                console.log(nums);
                if (!first) {
                    $.get("/user/list?currentPage=" + obj.curr + "&pageSize=" + nums + "&keyWord=" + $(".search_input").val(), function (data) {
                        if (data.statusCode == 200) {
                            usersData = data.data.datas;
                        }
                    });
                }
                $(".users_content").html(renderDate(usersData, obj.curr));
                $('.users_list thead input[type="checkbox"]').prop("checked", false);
                form.render();

            }
        })
    }

})