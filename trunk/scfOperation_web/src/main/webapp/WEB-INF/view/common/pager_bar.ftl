<#if (tbData.pageInfo.totalCount>0)>
	<span>共${tbData.pageInfo.totalCount}条</span>
	<input id="selectedPage" value="${tbData.pageInfo.pageNo}" type="hidden"/>
	<input id="pageAction" type="hidden" value="${tbData.action}" />
	<input id="params" type="hidden"  value="${tbData.params}" />
	
	<ul class="pagination pagination-sm no-margin pull-right">
		<#if (tbData.pageInfo.sumPage>1)>
			<li><a href="${basePath}/${tbData.action}?currentPage=1${tbData.params}" title="首页">首页</a></li>
		</#if>
		<#if (tbData.pageInfo.pageNo>1)>
			<li><a href="${basePath}/${tbData.action}?currentPage=${tbData.pageInfo.pageNo-1}${tbData.params}" title="上一页">上页</a></li>
		<#else>
			<li><a href="javascript:void(0);">上页</a></li>
		</#if>
		
		<#if tbData.pageList??>
			<#list tbData.pageList as pg>
				<#if !pg.isHidden>
					<#if pg.isCurrentPage>
						<li><a href="javascript:void(0);">${pg.pageNo}</a></li>
					<#else>
						<li><a href="${basePath}/${tbData.action}?currentPage=${pg.pageNo}${tbData.params}">${pg.pageNo}</a></li>
					</#if>
				</#if>
			</#list>
		</#if>
		
		<#if (tbData.pageInfo.pageNo<tbData.pageInfo.sumPage)>
			<li><a href="${basePath}/${tbData.action}?currentPage=${tbData.pageInfo.pageNo+1}${tbData.params}" title="下一页">下页</a></li>
		<#else>
			<li><a href="javascript:void(0);" >下页</a></li>
		</#if>
		<#if (tbData.pageInfo.sumPage>1)>
			<li><a href="${basePath}/${tbData.action}?currentPage=${tbData.pageInfo.sumPage}${tbData.params}" title="尾页">尾页</a></li>
		</#if>
	</ul>
</#if>