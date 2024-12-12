package com.example.demo.controller;

import com.example.demo.Config.TokenRequired;
import com.example.demo.Enums.ServiceResultEnum;
import com.example.demo.Enums.UserType;
import com.example.demo.Util.Result;
import com.example.demo.Util.ResultGenerator;
import com.example.demo.controller.Param.ProductAddParam;
import com.example.demo.controller.Param.ProductUpdateParam;
import com.example.demo.controller.Param.StoreAddParam;
import com.example.demo.controller.Param.StoreUpdateInfoParam;
import com.example.demo.dao.ProductMapper;
import com.example.demo.dao.StoreMapper;
import com.example.demo.dao.UserMapper;
import com.example.demo.entity.Store;
import com.example.demo.entity.User;
import com.example.demo.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.Enums.ServiceResultEnum.*;

@RestController
@Tag(name="店铺接口", description = "")
@RequestMapping("/api/v1")
public class StoreController {
    @Autowired
    StoreMapper storeMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    UserMapper userMapper;
    @Resource
    private StoreService storeService;

    @GetMapping("/store/findAll")
    @Operation(summary = "查找所有店铺接口", description = "")
    public List<Store> findAll(){
        return storeMapper.findAllStores();
    }

    @PostMapping("/store/add_store")
    @Operation(summary="添加店铺接口", description = "")
    public Result addStore(@RequestBody @Valid @Parameter(name="storeAddParam", description = "")StoreAddParam storeAddParam,
                           @TokenRequired User user){
        if(user.getUserType()!= UserType.MERCHANT){
            return ResultGenerator.genFailResult(USER_TYPE_ERROR.getResult());
        }
        storeAddParam.setUserId(user.getUserId());
        ServiceResultEnum serviceResult=storeService.addStore(storeAddParam);
        if(serviceResult==SUCCESS){
            return ResultGenerator.genSuccessResult();
        }
        else if(serviceResult==UPDATE_FAILED){
            return ResultGenerator.genFailResult(serviceResult.getResult());
        }
        return null;
    }

    @PutMapping("/store/edit")
    @Operation(summary = "修改店铺信息接口",description = "")
    public Result updateStoreInfo(@RequestBody @Parameter(name="storeUpdateInfoParam")StoreUpdateInfoParam storeUpdateInfoParam,
                             @TokenRequired User user){
        if(user.getUserType()!= UserType.MERCHANT){
            return ResultGenerator.genFailResult(USER_TYPE_ERROR.getResult());
        }
        ServiceResultEnum serviceResult=storeService.updateInfo(storeUpdateInfoParam);
        if(serviceResult==SUCCESS){
            return ResultGenerator.genSuccessResult();
        }
        else if(serviceResult==UPDATE_FAILED){
            return ResultGenerator.genFailResult(serviceResult.getResult());
        }
        return null;
    }

    @PostMapping("/store/add_product")
    @Operation(summary = "添加商品接口", description = "")
    public Result<String> addProduct(@RequestBody @Parameter(name="productAddParam")ProductAddParam productAddParam,
                             @TokenRequired User user){
        if(user.getUserType()!= UserType.MERCHANT){
            return ResultGenerator.genFailResult(USER_TYPE_ERROR.getResult());
        }
        ServiceResultEnum serviceResult=storeService.addProduct(productAddParam);
        if(serviceResult==SUCCESS){
            return ResultGenerator.genSuccessResult();
        }
        else{
            return ResultGenerator.genFailResult(serviceResult.getResult());
        }
    }

    @PutMapping("/store/edit_product")
    @Operation(summary = "修改商品接口", description = "")
    public Result updateProductInfo(@RequestBody @Parameter(name="productUpdateParam")ProductUpdateParam productUpdateParam,
                                    @TokenRequired User user){
        if(user.getUserType()!= UserType.MERCHANT){
            return ResultGenerator.genFailResult(USER_TYPE_ERROR.getResult());
        }
        ServiceResultEnum serviceResult=storeService.updateProduct(productUpdateParam);
        if(serviceResult==SUCCESS){
            return ResultGenerator.genSuccessResult();
        }
        else{
            return ResultGenerator.genFailResult(serviceResult.getResult());
        }
    }

    @GetMapping("/store/get_store_by_user")
    @Operation(summary = "查找用户名下店铺",description = "")
    public Result getStoreByUserId(@RequestBody String userId){

    }
}
