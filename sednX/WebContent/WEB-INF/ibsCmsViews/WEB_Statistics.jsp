<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href="${pageContext.request.contextPath}/ibsCmsCss/statistics/common.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/ibsCmsCss/statistics/jquery-ui.css" rel="stylesheet">

<div class="page">

	<div class="inner">

		<div class="left_container">
			<ul>
				<li class="menu01">
					<a href="sub_01.html">Contents</a>
					<ul class="depth2">
						<li><a href="sub_01.html">VOD</a></li>
						<li><a href="sub_02.html">LIVE</a></li>
						<li><a href="sub_03.html">OTT</a></li>
						<li><a href="sub_04.html">Conference</a></li>
						<li><a href="sub_05.html">UCC</a></li>
					</ul>
				</li>
				<li class="menu02 on">
					<a href="sub_01.html">Statistics</a>
					<ul class="depth2">
						<li class="on"><a href="sub_01.html">VOD 통계</a></li>
						<li><a href="sub_02.html">LIVE 시청 통계</a></li>
						<li><a href="sub_03.html">사용자 이용 통계</a></li>
						<li><a href="sub_04.html">방문자 통계</a></li>
						<li><a href="sub_05.html">서버 통계</a></li>
					</ul>
				</li>
				<li class="menu03"><a href="#">Management</a></li>					
			</ul>
		</div>
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
							메뉴리스트
						</div>
					</div>
					<div class="right" style="position: relative; left: 25%; width: 75%;">
						<div class="text-left mb10">
							<button class="disapp">전체선택</button>
							<button class="disapp">삭제</button>
						</div>
						<div class="table_container text-center">
							<a href="sub_01_1.html">
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
							</a>
							<!-- pagination -->
			                <div class="pagination_container text-center">
			                    <ul class="pagination">
			                        <li><a href="#"><img src="../img/arr_left02.png" alt="맨처음" /></a></li>
			                        <li><a href="#"><img src="../img/arr_left.png" alt="이전" /></a></a></li>
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
			                        <li><a href="#"><img src="../img/arr_right.png" alt="다음" /></a></a></li>
			                        <li><a href="#"><img src="../img/arr_right02.png" alt="마지막" /></a></a></li>
			                    </ul>
			                </div><!-- //pagination -->
						</div>
					</div>
				</div>
			</div> <!-- //contents_container -->

		</div> <!-- //contents -->

	</div> <!-- //inner -->

</div> <!-- //page -->

<script src="${pageContext.request.contextPath}/ibsCmsJs/statistics/common.js"></script>
<script src="${pageContext.request.contextPath}/ibsCmsJs/statistics/Chart.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/ibsCmsJs/statistics/Chart.min.js"></script>
<script src="${pageContext.request.contextPath}/ibsCmsJs/statistics/utils.js"></script>
<script>
  $(function () {

  });
</script>
