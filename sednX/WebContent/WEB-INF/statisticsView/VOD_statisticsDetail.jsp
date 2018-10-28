<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="contents">
	<div class="sub_header">
		<ol class="location">
			<li class="li01"><img src="../img/icon_home.png" alt="홈 아이콘"/></li>
			<li class="li02">Statistics</li>
			<li class="li03">VOD 통계<span></span></li>
		</ol>
		<h1>VOD 통계</h1>
	</div>

	<div class="contents_container">
		<div class="leftRight_contents">
			<h2>${map.vod_title}</h2><br/>
			<div class="left">
				<div class="vedio">
					<div class="img_box" id="layer_${map.idx}" style="background: url('${pageContext.request.contextPath}${map.main_thumbnail}') no-repeat center; background-size: cover;">
						<input class="pull-left m-l-5 vodCheck" type="checkbox" value="${map.idx}"/>
						<input class="pull-left m-l-5 vodRadio" type="radio"  name="redioVal" value="${map.idx}" title="${map.vod_path}" />
						<div class="imgPopup" id="${map.idx}"  style="left:30px;width:90%;height:100%;position:relative;"></div>
					</div>
				</div>
			</div>		
			<div class="right">							
				<div class="table_container text-center mb30">
					<table summary="통계 분석/이 표는 날짜, 재생시간, 재생 수, 평균 재생 시간으로 구성">
						<colgroup>
							<col width="auto;" />
							<col width="12%;" />
							<col width="12%;" />
							<col width="12%;" />
							<col width="12%;" />
							<col width="12%;" />
							<col width="12%;" />
							<col width="12%;" />
						</colgroup>
						<thead>
							<tr>
								<th>카테고리</th>
								<th>영상 시간</th>
								<th>총 조회 수</th>
								<th>OTT</th>
								<th>APP</th>
								<th>Web(PC)</th>
								<th>SNS 공유수</th>
								<th>평균 재생 시간</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${map.category_name}</td>
								<td>${map.vod_play_time}</td>
								<td>3050</td>
								<td>155</td>
								<td>2012</td>
								<td>393</td>
								<td>1069</td>
								<td>90%</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>	
		<div class="graph mb50">
			<canvas id="time" style="height: 300px;"></canvas>
		</div>
		<div class="search_form mb30 text-center" style="height: 30px; line-height: 30px;">
			<div class="div_form">
				<div class="div_group">
					기간 검색 :
					<label for="">기간검색시작</label>
					<input type="text" class="datepicker" id="" /> ~
				</div>
				<div class="div_group mr50">
					<label for="">기간검색종류</label>
					<input type="text" class="datepicker" id="" />
				</div>
				<div class="div_group">
					<a class="list-a border" href="#">전일</a><a class="list-a" href="#">7일</a><a class="list-a" href="#">15일</a><a class="list-a" href="#">1개월</a><a class="list-a" href="#">3개월</a>
				</div>
				<button class="btn-search">검색</button>
			</div>
		</div>
		<div class="text-right">
			<button class="btn btn-excel">엑셀로 저장</button>
		</div>
		<div class="site_container">
			<div class="table_container text-center">
				<table summary="통계 분석/이 표는 날짜, 재생시간, 재생 수, 평균 재생 시간으로 구성">
					<colgroup>
						<col width="auto;" />
					</colgroup>
					<thead>
						<tr>
							<th>날짜</th>
							<th>총 조회 수</th>
							<th>OTT</th>
							<th>APP</th>
							<th>Web(PC)</th>
							<th>재생시간</th>
							<th>평균 재생 시간</th>
							<th>평균 재생 구간</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>2018.09.06</td>
							<td>1,069</td>
							<td>469</td>
							<td>300</td>
							<td>300</td>
							<td>03:37:04</td>
							<td>00:00:29</td>
							<td>90%</td>
						</tr>
						<tr>
							<td>2018.09.06</td>
							<td>1,069</td>
							<td>469</td>
							<td>300</td>
							<td>300</td>
							<td>03:37:04</td>
							<td>00:00:29</td>
							<td>90%</td>
						</tr>
						<tr>
							<td>2018.09.06</td>
							<td>1,069</td>
							<td>469</td>
							<td>300</td>
							<td>300</td>
							<td>03:37:04</td>
							<td>00:00:29</td>
							<td>90%</td>
						</tr>
						<tr>
							<td>2018.09.06</td>
							<td>1,069</td>
							<td>469</td>
							<td>300</td>
							<td>300</td>
							<td>03:37:04</td>
							<td>00:00:29</td>
							<td>90%</td>
						</tr>
						<tr>
							<td>2018.09.06</td>
							<td>1,069</td>
							<td>469</td>
							<td>300</td>
							<td>300</td>
							<td>03:37:04</td>
							<td>00:00:29</td>
							<td>90%</td>
						</tr>
						<tr>
							<td>2018.09.06</td>
							<td>1,069</td>
							<td>469</td>
							<td>300</td>
							<td>300</td>
							<td>03:37:04</td>
							<td>00:00:29</td>
							<td>90%</td>
						</tr>
						<tr>
							<td>2018.09.06</td>
							<td>1,069</td>
							<td>469</td>
							<td>300</td>
							<td>300</td>
							<td>03:37:04</td>
							<td>00:00:29</td>
							<td>90%</td>
						</tr>
						<tr>
							<td>2018.09.06</td>
							<td>1,069</td>
							<td>469</td>
							<td>300</td>
							<td>300</td>
							<td>03:37:04</td>
							<td>00:00:29</td>
							<td>90%</td>
						</tr>
						<tr>
							<td>2018.09.06</td>
							<td>1,069</td>
							<td>469</td>
							<td>300</td>
							<td>300</td>
							<td>03:37:04</td>
							<td>00:00:29</td>
							<td>90%</td>
						</tr>
						<tr>
							<td>2018.09.06</td>
							<td>1,069</td>
							<td>469</td>
							<td>300</td>
							<td>300</td>
							<td>03:37:04</td>
							<td>00:00:29</td>
							<td>90%</td>
						</tr>
					</tbody>
				</table>
			</div>

			<!-- pagination -->
               <div class="pagination_container text-center">
                   <ul class="pagination">
                       <li><a href="#"><img src="../img/arr_left02.png" alt="맨처음" /></a></li>
                       <li><a href="#"><img src="../img/arr_left.png" alt="이전" /></a></li>
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
                       <li><a href="#"><img src="../img/arr_right.png" alt="다음" /></a></li>
                       <li><a href="#"><img src="../img/arr_right02.png" alt="마지막" /></a></li>
                   </ul>
               </div><!-- //pagination -->
		</div>			

		<script>
			
			var config2 = {
				type: 'line',
				data: {
					labels: ['10.08','10.09', '10.10', '10.11', '10.12', '10.13', '10.14', '10.15', '10.16', '10.17', '10.18', '10.19', '10.20', '10.21'],
					datasets: [{
						label: 'OTT',
						fill: false,
						backgroundColor: window.chartColors.red,
						borderColor: window.chartColors.red,
						data: [40,50,60,120,40,30,10,20,30,70,10,20,30,40],
					}, {									
						label: 'APP',
						backgroundColor: window.chartColors.blue,
						borderColor: window.chartColors.blue,
						data: [50,60,80,90,30,40,50,60,70,20,30,50,80,70],
						fill: false,
					}, {									
						label: 'Web(PC)',
						backgroundColor: window.chartColors.orange,
						borderColor: window.chartColors.orange,
						data: [10,20,30,40,60,70,80,90,50,60,60,40,20,30],
						fill: false,
					}]
				},
				options: {
					maintainAspectRatio: false, //높이조절
					responsive: true,
					tooltips: {
						mode: 'index',
						intersect: false,
					},
					hover: {
						mode: 'nearest',
						intersect: true
					},
					scales: {
						yAxes: [{
							ticks: {
								min: 0,
								max: ${map.idx}
							}
						}]
					}
				}
			};
			

			window.onload = function() {
				var ctx2 = document.getElementById('time').getContext('2d');
				window.myLine = new Chart(ctx2, config2);				
			};	
		</script>

	</div> <!-- //contents_container -->

</div> <!-- //contents -->

<script>
  $(function () {

  });
</script>
