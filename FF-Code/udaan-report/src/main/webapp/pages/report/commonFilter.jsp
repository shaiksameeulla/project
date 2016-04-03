	<td class="lable"><sup class="star">* </sup> <bean:message
			key="label.consgBookingReport.region" /></td>

	<td><c:choose>
			<c:when test="${wrapperReportTO.getRegionTO().size() == 1}">

				<select name="to.regionTo" id="regionList"
					class="selectBox width130" disabled="disabled">
					<option
						value="${wrapperReportTO.getRegionTO().get(0).getRegionId() }"
						selected="selected">${
						wrapperReportTO.getRegionTO().get(0).getRegionName()}</option>
				</select>
			</c:when>
			<c:when test="${wrapperReportTO.getRegionTO().size() == 0}">
				<select name="to.regionTo" id="regionList"
					class="selectBox width130" >
					<option value="Select">Select</option>
				</select>

			</c:when>
			<c:otherwise>
				<select name="to.regionTo" id="regionList"
					class="selectBox width130"
					onchange="getStationsList('regionList', 'stationList,branchList,destRegionList');">
					<option value="Select">Select</option>
					<c:forEach var="regions" items="${wrapperReportTO.getRegionTO()}">
						<option value="${regions.regionId }">${regions.regionName}</option>
					</c:forEach>
				</select>
			</c:otherwise>
		</c:choose></td>

	<td class="lable"> </sup> <bean:message
			key="label.consgBookingReport.station" /></td>


	<td><c:choose>
			<c:when test="${wrapperReportTO.getCityTO().size() == 1}">
				<select name="to.station" id="stationList"
					class="selectBox width130" disabled="disabled">
					<option value="${wrapperReportTO.getCityTO().get(0).getCityId() }"
						selected="selected">
						${wrapperReportTO.getCityTO().get(0).getCityName()}</option>
				</select>
			</c:when>
			<c:when test="${wrapperReportTO.getCityTO().size() == 0}">
				<select name="to.station" id="stationList"
					class="selectBox width130" >
					<option value="Select">Select</option>
				</select>

			</c:when>
			<c:otherwise>
				<select name="to.station" id="stationList"
					class="selectBox width130" >
					<option value="Select">Select</option>
					<option value="All">All</option>
					<c:forEach var="city" items="${wrapperReportTO.getCityTO()}">
						<option value="${city.cityId }" >
							${city.cityName}</option>
					</c:forEach>

				</select>
			</c:otherwise>

		</c:choose></td>
