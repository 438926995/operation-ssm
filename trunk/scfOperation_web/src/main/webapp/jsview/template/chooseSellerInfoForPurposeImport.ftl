/**
 * 选择商户
 */
<script id="chooseSellerInfoForPurposeImport" type="text/html">
	<div class="box-body">
  		<table class="table table-bordered table-hover">
           <thead>
                      <tr>
                        <th>Napos餐厅id</th>
						<th>餐厅名称</th>
						<th>手机号码</th>
						<th>地址</th>
						<th>城市id</th>
						<th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    {{each list as value i }}
						<tr>
							<td style="text-align:left">{{value.oid}}</td>
							<td style="text-align:left">{{value.name}}</td>
							<td style="text-align:left"></td>
							<td style="text-align:left"></td>
							<td style="text-align:left"></td>
							<td style="text-align:left">
								<span onclick="#" style="cursor:pointer">保存到商户基础表</span>
							</td>
						</tr>
					{{/each}}
                    </tbody>
                  </table>
                </div>
</script>
<#-- 显示根据id查询金融机构信息 -->
<script id="chooseFinanceInfoForPurposeImport" type="text/html">
	<div class="box-body">
  		<table class="table table-bordered table-hover">
            <tbody>
				<tr>
					<td>机构名称</td>
					<td style="text-align:left">{{financeInfo.foName}}</td>
				</tr>
			</tbody>
		</table>
	</div>
</script>
