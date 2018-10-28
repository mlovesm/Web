<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hn" uri="/WEB-INF/tlds/hanibalWebDev.tld"%>
<script src="${pageContext.request.contextPath}/ibsCmsJs/functionInc.js"></script>

<div class="contents">
	<div class="sub_header">
		<ol class="location">
			<li class="li01"><img src="${pageContext.request.contextPath}/statistics/img/icon_home.png" alt="홈 아이콘"/></li>
			<li class="li02">Statistics</li>
			<li class="li03">VOD 통계<span></span></li>
		</ol>
		<h1>VOD 통계</h1>
	</div>

	<div class="contents_container">		
		<div class="search_form mb30 text-right" style="height: 30px; line-height: 30px;">
			<div class="div_form">
				<!-- <div class="div_group">
					<label for="">검색</label>
					<select name="" id="">
						<option value="">VOD</option>
						<option value=""></option>
						<option value=""></option>
					</select>
				</div> -->
				<div class="div_group">
					<label for="">검색</label>
					<input type="text" id="" />
				</div>
				<!-- <div class="div_group">
					<a class="list-a border" href="#">전일</a><a class="list-a" href="#">7일</a><a class="list-a" href="#">15일</a><a class="list-a" href="#">1개월</a><a class="list-a" href="#">3개월</a>
				</div> -->
				<button class="btn-search">검색</button>
			</div>
		</div>

		<!-- <div class="video_container">
			<div class="video">
				<h2>가장 인기있는 동영상</h2>
				<div class="border">(동영상 데이터)</div>
			</div>
		</div> -->
		<div style="position: relative;">
			<div class="left" style="position: absolute; left: 0; width: 22%; height: 100%;">
				<div class="border" style="height: 90%;">
		   			<!-- TREE START-->
					<div id="menuTree" style="margin:10px;"></div>
					<!-- TREE END-->
				</div>
			</div>
			<div class="right" style="position: relative; left: 25%; width: 75%;">
				<div class="text-left mb10">
					<button class="disapp">전체선택</button>
					<button class="disapp">삭제</button>
				</div>
				<div id="grid"></div>			
				
				<div class="table_container text-center">
					<table summary="통계 분석/이 표는 으로 구성">
						<colgroup>
							<col width="80px;" />
							<col width="auto;" />
							<col width="150px;" />
							<col width="150px;" />
							<col width="150px;" />
							<col width="100px;" />
							<col width="100px;" />
						</colgroup>
						<thead>
							<tr>
								<th>
									<label class="hide" for="check01">체크박스</label>
									<input type="checkbox" name="checkbox" id="check01" />
								</th>
								<th>제목</th>
								<th>카테고리</th>
								<th>영상시간</th>
								<th>등록일</th>
								<th>총 조회수</th>
								<th>SNS 공유수</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>
									<label class="hide" for="check01">체크박스</label>
									<input type="checkbox" name="checkbox" id="check01" />
								</td>
								<td>영상제목...</td>
								<td>카테고리...</td>
								<td>00:00:00</td>
								<td>2018.10.15</td>
								<td>3,123</td>
								<td>198</td>
							</tr>						
						</tbody>
					</table>
					
					<!-- pagination -->
	                <div class="pagination_container text-center">
	                    <ul class="pagination">
	                        <li><a href="#"><img src="${pageContext.request.contextPath}/statistics/img/arr_left02.png" alt="맨처음" /></a></li>
	                        <li><a href="#"><img src="${pageContext.request.contextPath}/statistics/img/arr_left.png" alt="이전" /></a></li>
	                        <li><a href="#">1</a></li>
	                        <li><a href="#">2</a></li>
	                        <li><a href="#">3</a></li>
	                        <li><a href="#">4</a></li>
	                        <li><a href="#">5</a></li>
	                        <li><a href="#">6</a></li>
	                        <li><a href="#">7</a></li>
	                        <li><a href="#">8</a></li>
	                        <li><a href="#">9</a></li>
	                        <li><a href="#">10</a></li>
	                        <li><a href="#"><img src="${pageContext.request.contextPath}/statistics/img/arr_right.png" alt="다음" /></a></li>
	                        <li><a href="#"><img src="${pageContext.request.contextPath}/statistics/img/arr_right02.png" alt="마지막" /></a></li>
	                    </ul>
	                </div><!-- //pagination -->
				</div>
			</div>
		</div>
	</div> <!-- //contents_container -->

</div> <!-- //contents -->
	
<input type="hidden" class="form-control" id="vodOrder" value="insert">
<input type="hidden" class="form-control" id="photoOrder" value="insert">
<input type="hidden" class="form-control" id="fileOrder" value="insert">
<input type="hidden" class="form-control" id="streamOrder" value="insert">
<input type="hidden" class="form-control" id="sort" value="vod">
<input type="hidden" class="form-control" id="categoryIdx" />
<input type="hidden" class="form-control" id="categoryName"/>
<input type="hidden" class="form-control" id="treeIdx" >
<input type="hidden" class="form-control" id="treeProperty" value="1">
<input id="repoOrder" type="hidden" class="form-control"/>
<input id="requestRepo" type="hidden" class="form-control" value="media">

<script>
$('#categoryIdx').val('${hn:getDefaultContentsIdx()}');
$('#categoryName').val('${hn:getDefaultContentsParentName()}<i class="fa fa-angle-right m-r-10 m-l-10"></i><i class="fa fa-list-alt m-r-10"></i>${hn:getDefaultContentsName()}');

var menuJs={
		makeJsTree:function(idx){
			$.ajax({
				url : "${pageContext.request.contextPath}/api/statisticsTree/"
						+ $("#sort").val()+"/"+idx,
				async : false,
				success : function(data) {
					$("#menuTree").empty();
					$("#menuTree").html(data);
				},
				error : exception.ajaxException
			});
		},
		getData: function(idx){
			$.ajax({
				url : "${pageContext.request.contextPath}/statistics/vod/VODListData/"+idx,
				async : false,
				success : function(data) {
					console.log(data);
				},
			});
		}

};
menuJs.makeJsTree($('#categoryIdx').val());

/* $.ajax({
    url: menuJs.getData($('#categoryIdx').val()),
    responseTime: 0,
    response: function(settings) {
        var page = settings.data.page;
        var perPage = settings.data.perPage;
        var start = (page - 1) * perPage;
        var end = start + perPage;
        var data = gridData.slice(start, end);

        this.responseText = JSON.stringify({
            result: true,
            data: {
                contents: data,
                pagination: {
                    page: page,
                    totalCount: 20
                }
            }
        });
    }
}); */

var grid = new tui.Grid({
    el: $('#grid'),
    scrollX: false,
    scrollY: false,
    pagination: true,
   /*  minBodyHeight: 300, */
    rowHeaders: [
    	{
    		type: 'checkbox',
    	}
    ],
    columns: [
        {
            title: '제목',
            name: 'vod_title',
            align: 'center'
            /* sortable: true, */
        },
        {
            title: '카테고리',
            name: 'category_name',
            align: 'center'
        },
        {
            title: '영상시간',
            name: 'vod_play_time',
            align: 'center'
        },
        {
            title: '등록일',
            name: 'reg_dt',
            align: 'center'
        },
        {
            title: '총 조회수',
            name: 'vod_title_link',
            align: 'center'
        }
    ],

});

tui.Grid.applyTheme('striped', {
    grid: {
        background: '#fff',
        border: '#ddd',
        text: '#444'
    },
    cell: {
    	background: '#fff',
    }
});

grid.use('Net', {
    //perPage: 3,
    readDataMethod: 'GET',
    initialRequest: true,
    api: {
        'readData': "${pageContext.request.contextPath}/statistics/vod/VODListData/"+$('#categoryIdx').val(),
        'createData': './api/create',
        'updateData': './api/update',
        'deleteData': './api/delete',
        'modifyData': './api/modify',
        'downloadExcel': './api/download/excel',
        'downloadExcelAll': './api/download/excelAll'
    }
});


// Bind event handlers
grid.on('beforeRequest', function(data) {
	console.log('beforeRequest', data);
	
}).on('response', function(data) {
	console.log('response', data);
	
}).on('successResponse', function(data) {
	console.log('successResponse', data);
	
}).on('failResponse', function(data) {
	console.log('failResponse', data);
    
}).on('errorResponse', function(data) {
	console.log('errorResponse', data);
});

grid.on('click', function(ev) {
	var target = ev.nativeEvent.target;
	var obj = grid.getRow(ev.rowKey);
	if(obj != null) {
		location.href= "./vodDetail?idx="+obj.idx;
	}
    console.log(obj, target);
});

grid.on('selectRow', function(ev) {
	console.log('selectRow', obj, target);
    if (ev.rowKey === 3) {
      ev.stop();  
    }
});

grid.on('check', function(ev) {
    console.log('check', ev);
});

grid.on('uncheck', function(ev) {
    console.log('uncheck', ev, grid.getRowCount());
});

$(document).ready(function() {
	$(".btn-search").bind('click', function(){
		var net = grid.getAddOn('Net');
		//net.download('excel');
		//net.reloadData();
		//net.readData(2, {"test":"test"}, true);
		var options = {
			'childIdx': $('#categoryIdx').val()
		};
		//net.request('readData', options);
		//net.readData(1, {"childIdx":$('#categoryIdx').val()}, true);
	});
});




</script>