package com.warehouse.backend_system.service.impl;

import com.warehouse.backend_system.dto.DashboardStatDTO;
import com.warehouse.backend_system.mapper.MaterialBillMapper;
import com.warehouse.backend_system.mapper.MaterialMapper;
import com.warehouse.backend_system.mapper.StockRecordMapper;
import com.warehouse.backend_system.mapper.SysUserMapper;
import com.warehouse.backend_system.service.DashboardService;
import com.warehouse.backend_system.entity.Material;
import com.warehouse.backend_system.entity.MaterialBill;
import com.warehouse.backend_system.vo.DashboardChartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private MaterialMapper materialMapper;
    @Resource
    private MaterialBillMapper materialBillMapper;

    @Autowired
    private StockRecordMapper stockRecordMapper;
    @Override
    public DashboardStatDTO getDashboardStat() {
        DashboardStatDTO dto = new DashboardStatDTO();
        // 用户总数量
        dto.setUserTotal(sysUserMapper.selectUserCount());
        //物料总种类
        dto.setMaterialTotal(materialMapper.selectMaterialCount());
        //今日入库、出库单数量 1入库 2出库
        LocalDate today = LocalDate.now();
        dto.setTodayIn(materialBillMapper.countTodayBill(today,1));
        dto.setTodayOut(materialBillMapper.countTodayBill(today,2));
        return dto;
    }

    //下面补上另外三个接口实现（必须补齐，否则启动报错，接口方法未实现）
    @Override
    public List<DashboardChartVO> getSevenDayChart() {
        //1 查询数据库有业务记录的日期统计
        List<DashboardChartVO> dbList = stockRecordMapper.selectSevenDayRecord();

        //转Map，key：日期字符串 "2026-08-15"
        Map<String, DashboardChartVO> dataMap = dbList.stream()
                .collect(Collectors.toMap(DashboardChartVO::getDate, vo -> vo));

        List<DashboardChartVO> result = new ArrayList<>();
        LocalDate today = LocalDate.now();
        //往前循环6天，加上今天一共7天
        for (int i = 6; i >= 0; i--) {
            LocalDate day = today.minusDays(i);
            String dayStr = day.toString();
            DashboardChartVO vo = dataMap.get(dayStr);
            if (vo == null) {
                //该日期数据库无记录，新建VO，数量置0
                vo = new DashboardChartVO();
                vo.setDate(dayStr);
                vo.setInCount(0);
                vo.setOutCount(0);
            }
            result.add(vo);
        }
        return result;
    }

    @Override
    public List<Material> getWarnMaterialList() {
        return materialMapper.selectWarnMaterial();
    }

    @Override
    public List<MaterialBill> getLatestBill() {
        return materialBillMapper.selectLatestBill();
    }
}