package com.warehouse.backend_system.controller;

import com.warehouse.backend_system.dto.BillSubmitDTO;
import com.warehouse.backend_system.entity.MaterialBill;
import com.warehouse.backend_system.vo.BillVO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.warehouse.backend_system.service.MaterialBillService;
import com.warehouse.backend_system.util.Result;

import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.Resource;

@RestController
@RequestMapping("/materialBill")
public class MaterialBillController {
    @Resource
    private MaterialBillService materialBillService;

    @RequestMapping("/listAll")

    public Result<List<MaterialBill>> getAll(){
        List<MaterialBill> list = materialBillService.getAll();
        return Result.success(list);
    }

    @GetMapping("/list")
    public Result<List<MaterialBill>> list(
            @RequestParam(required = false) Integer billType,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy‑MM‑dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy‑MM‑dd HH:mm:ss") LocalDateTime endTime
    ){
        List<MaterialBill> list = materialBillService.getList(billType, startTime, endTime);
        return Result.success(list);
    }

    @GetMapping("/info/{id}")
    public Result<MaterialBill> getById(@PathVariable Long id){
        MaterialBill bill = materialBillService.getById(id);
        if(bill == null){
            return Result.fail(500,"单据不存在");
        }
        return Result.success(bill);
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody MaterialBill bill){
        boolean ok = materialBillService.add(bill);
        if(ok){
            return Result.success("新增单据成功");
        }else{
            return Result.fail(500,"单据编号已存在，新增失败");
        }
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody MaterialBill bill) {
        try {
            boolean ok = materialBillService.update(bill);
            if (ok) {
                return Result.success("修改单据成功");
            } else {
                return Result.fail(500, "修改失败，单据不存在或单据编号冲突");
            }
        } catch (RuntimeException e) {
            return Result.fail(500, e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id){
        try {
            boolean ok = materialBillService.remove(id);
            if(ok){
                return Result.success("删除成功");
            }else{
                return Result.fail(500,"删除失败");
            }
        }catch (RuntimeException e){
            return Result.fail(500,e.getMessage());
        }
    }

    /**
     * 提交出入库单据接口
     */
    @PostMapping("/submit")
    public Result<String> submit(@RequestBody BillSubmitDTO dto){
        try {
            boolean ok = materialBillService.submitBill(
                    dto.getBillNo(),
                    dto.getBillType(),
                    dto.getOperatorId(),
                    dto.getRemark(),
                    dto.getItemList()
            );
            if(ok){
                return Result.successMsg("单据提交成功");
            }else{
                return Result.fail(500,"单据提交失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(500,e.getMessage());
        }
    }
    /**
     * 单据列表查询
     * @param billNo 单据号（可选）
     * @param billType 单据类型1入库 2出库（可选）
     * @return
     */
    @GetMapping("/billlist")
    public Result<List<MaterialBill>> billList(String billNo,Integer billType){
        List<MaterialBill> list = materialBillService.getBillList(billNo,billType);
        return Result.success(list);
    }

    /**
     * 获取单据详情，携带明细
     * @param id 单据id
     * @return
     */
    @GetMapping("/detail/{id}")
    public Result<BillVO> billDetail(@PathVariable Long id){
        BillVO vo = materialBillService.getBillDetail(id);
        return Result.success(vo);
    }

    @GetMapping("/latest")
    public Result<List<MaterialBill>> latestBill(){
        List<MaterialBill> list = materialBillService.getLatestBill();
        return Result.success(list);
    }


}
