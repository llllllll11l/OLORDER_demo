package com.example.demo.controller;

import com.example.demo.Config.TokenRequired;
import com.example.demo.Enums.ServiceResultEnum;
import com.example.demo.Enums.UserType;
import com.example.demo.Util.PageQuery;
import com.example.demo.Util.Result;
import com.example.demo.Util.ResultGenerator;
import com.example.demo.controller.Param.ProductAddParam;
import com.example.demo.controller.Param.ProductUpdateParam;
import com.example.demo.controller.Param.StoreAddParam;
import com.example.demo.controller.Param.StoreUpdateInfoParam;
import com.example.demo.controller.vo.ProductListVO;
import com.example.demo.controller.vo.StoreListVO;
import com.example.demo.controller.vo.StoreVO;
import com.example.demo.dao.ProductMapper;
import com.example.demo.dao.StoreMapper;
import com.example.demo.dao.UserMapper;
import com.example.demo.entity.Product;
import com.example.demo.entity.Store;
import com.example.demo.entity.User;
import com.example.demo.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.Constants.Constants.UPLOAD_IMAGE_DIR;
import static com.example.demo.Enums.ServiceResultEnum.*;

@RestController
@Tag(name = "店铺接口", description = "")
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
    public List<Store> findAll() {
        return storeMapper.findAllStores();
    }

    @PostMapping("/store/add")
    @Operation(summary = "添加店铺接口", description = "")
    public Result<String> addStore(@RequestBody @Valid @Parameter(name = "storeAddParam", description = "") StoreAddParam storeAddParam,
                                   @TokenRequired User user) {
        if (user.getUserType() != UserType.MERCHANT) {
            return ResultGenerator.genFailResult(USER_TYPE_ERROR.getResult());
        }
        String serviceResult = storeService.addStore(storeAddParam, user.getUserId());
        if (!serviceResult.equals("UPDATE_FAILED")) {
            return ResultGenerator.genSuccessResult(serviceResult);
        } else {
            return ResultGenerator.genFailResult(serviceResult);
        }
    }

    @DeleteMapping("/store/{storeId}/del")
    @Operation(summary = "删除店铺接口", description = "根据ID删除店铺")
    public Result<String> delStore(@PathVariable("storeId") String storeId) {
        ServiceResultEnum serviceResult = storeService.delStore(storeId);

        return null;
    }

    @PutMapping("/store/{storeId}/edit")
    @Operation(summary = "修改店铺信息接口", description = "")
    public Result<String> updateStoreInfo(@RequestBody @Parameter(name = "storeUpdateInfoParam") StoreUpdateInfoParam storeUpdateInfoParam,
                                          @PathVariable("storeId") String storeId,
                                          @TokenRequired User user) {
        if (user.getUserType() != UserType.MERCHANT) {
            return ResultGenerator.genFailResult(USER_TYPE_ERROR.getResult());
        }
        ServiceResultEnum serviceResult = storeService.updateInfo(storeUpdateInfoParam, storeId);
        if (serviceResult == SUCCESS) {
            return ResultGenerator.genSuccessResult();
        } else if (serviceResult == UPDATE_FAILED) {
            return ResultGenerator.genFailResult(serviceResult.getResult());
        }
        return null;
    }

    @PostMapping("/store/{storeId}/add_product")
    @Operation(summary = "添加商品接口", description = "")
    public Result<String> addProduct(@RequestBody @Parameter(name = "productAddParam") ProductAddParam productAddParam,
                                     @PathVariable("storeId") String storeId,
                                     @TokenRequired User user) {
        if (user.getUserType() != UserType.MERCHANT) {
            return ResultGenerator.genFailResult(USER_TYPE_ERROR.getResult());
        }
        List<Store> storeList = storeMapper.selectByUserId(user.getUserId());
        Store store = storeMapper.selectByStoreId(storeId);
        if (store == null) {
            return ResultGenerator.genFailResult(STORE_NOT_FOUND.getResult());
        }
        if (!storeList.contains(store)) {
            return ResultGenerator.genFailResult(NO_PERMISSION_TO_STORE.getResult());
        }
        String serviceResult = storeService.addProduct(productAddParam, storeId);
        if (!serviceResult.equals("ADD_PRODUCT_FAILED")) {
            return ResultGenerator.genSuccessResult(serviceResult);
        } else {
            return ResultGenerator.genFailResult(serviceResult);
        }
    }

    @PutMapping("/store/{storeId}/products/{productId}/edit")
    @Operation(summary = "修改商品接口", description = "")
    public Result<String> updateProductInfo(@RequestBody @Parameter(name = "productUpdateParam") ProductUpdateParam productUpdateParam,
                                            @PathVariable("storeId") String storeId,
                                            @PathVariable("productId") String productId,
                                            @TokenRequired User user) {
        if (user.getUserType() != UserType.MERCHANT) {
            return ResultGenerator.genFailResult(USER_TYPE_ERROR.getResult());
        }
        List<Store> storeList = storeMapper.selectByUserId(user.getUserId());
        Store store = storeMapper.selectByStoreId(storeId);
        if (store == null) {
            return ResultGenerator.genFailResult(STORE_NOT_FOUND.getResult());
        }
        if (!storeList.contains(store)) {
            return ResultGenerator.genFailResult(NO_PERMISSION_TO_STORE.getResult());
        }
        ServiceResultEnum serviceResult = storeService.updateProduct(productUpdateParam, storeId, productId);
        if (serviceResult == SUCCESS) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(serviceResult.getResult());
        }
    }

    /*新改*/
    @Operation(summary = "上传商品图片接口")
    @PostMapping(value = "/store/{storeId}/products/{productId}/upload_image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<String> uploadProductImage(
            @PathVariable("storeId") String storeId,
            @PathVariable("productId") String productId,
            @RequestPart("file") MultipartFile file, //使用 @RequestPart而非 @RequestParam
            @TokenRequired User user) {
        Store store = storeMapper.selectByStoreId(storeId);
        Product product = productMapper.selectByProductId(productId);
        if (store == null || product == null) {
            return ResultGenerator.genFailResult("STORE OR PRODUCT NOT FOUND");
        }
        if (file.isEmpty()) {
            return ResultGenerator.genFailResult("FILE NOT FOUND");
        }
        if (user.getUserType() != UserType.MERCHANT) {
            return ResultGenerator.genFailResult("USER TYPE ERROR");
        }
        List<Store> storeList = storeMapper.selectByUserId(user.getUserId());
        if (storeList == null || !storeList.contains(store)) {
            return ResultGenerator.genFailResult(NO_PERMISSION_TO_STORE.getResult());
        }
        try {
            File directory = new File(UPLOAD_IMAGE_DIR);
            if (!directory.exists() && !directory.mkdirs()) {
                return ResultGenerator.genFailResult("FAILED TO CREATE UPLOAD DIRECTORY");
            }

            String originalFileName = file.getOriginalFilename();
            String cleanFileName = originalFileName != null ? originalFileName.replaceAll("[^a-zA-Z0-9._-]", "_") : "unknown";
            String fileName = product.getProductName() + "_" + System.currentTimeMillis() + "_" + cleanFileName;
            Path path = Paths.get(UPLOAD_IMAGE_DIR, fileName);

            //校验文件类型
            String contentType = file.getContentType();
            if (contentType == null || (!contentType.startsWith("image/"))) {
                return ResultGenerator.genFailResult("INVALID FILE TYPE");
            }

            Files.copy(file.getInputStream(), path);

            String fileDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/images/")
                    .path(fileName)
                    .toUriString();

            //更新数据库
            String oldImagePath = product.getProductImage();
            product.setProductImage(fileDownloadUrl);
            if (productMapper.updateByProductId(product) > 0) {
                //删除旧图片（如果有）
                if (oldImagePath != null) {
                    File oldFile = new File(oldImagePath);
                    if (oldFile.exists() && !oldFile.delete()) {
                        System.err.println("Failed to delete old file: " + oldImagePath);
                    }
                }
                return ResultGenerator.genSuccessResult(fileDownloadUrl);
            } else {
                Files.deleteIfExists(path); // 数据库失败则删除新上传文件
                return ResultGenerator.genFailResult("UPLOAD IMAGE FAILED");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult("FILE UPLOAD ERROR: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult("UNKNOWN ERROR OCCURRED");
        }
    }
    /**/

    @GetMapping("/store/{storeId}/detail")
    @Operation(summary = "(客户或店主预览)查看店铺信息接口", description = "")
    public Result<StoreVO> getStoreDetail(@PathVariable("storeId") String storeId, @TokenRequired User user) {
        Store store = storeMapper.selectByStoreId(storeId);
        if (store == null || user == null) {
            return ResultGenerator.genSuccessResult("FAILED");
        }
        StoreVO storeVO = new StoreVO();
        BeanUtils.copyProperties(store, storeVO);
        return ResultGenerator.genSuccessResult(storeVO);
    }

    @GetMapping("/store/owner/{userId}")
    @Operation(summary = "查找用户名下店铺", description = "")
    public Result<List<StoreListVO>> getStoreByUserId(@PathVariable("userId") String userId,
                                                      @TokenRequired User user) {
        List<Store> storeList = storeMapper.selectByUserId(userId);
        if (storeList != null) {
            List<StoreListVO> storeListVOList = new ArrayList<>();
            for (Store i : storeList) {
                StoreListVO t = new StoreListVO();
                BeanUtils.copyProperties(i, t);
                storeListVOList.add(t);
            }
            return ResultGenerator.genSuccessResult(storeListVOList);
        }
        return ResultGenerator.genFailResult("FAILED");
    }

    @GetMapping("/store/{storeId}/products")
    @Operation(summary = "查找店铺里的商品", description = "")
    public Result<List<ProductListVO>> getProductsByStoreId(@PathVariable("storeId") String storeId,
                                                            @TokenRequired User user) {
        List<Product> list = productMapper.selectByStoreId(storeId);
        if (list != null) {
            List<ProductListVO> productListVOList = new ArrayList<ProductListVO>();
            for (Product i : list) {
                ProductListVO t = new ProductListVO();
                BeanUtils.copyProperties(i, t);
                productListVOList.add(t);
            }
            return ResultGenerator.genSuccessResult(productListVOList);
        }
        return ResultGenerator.genFailResult("FAILED");
    }

    @GetMapping("/store/list/search")
    @Operation(summary = "根据搜索分页查看店铺列表", description = "")
    public Result<List<StoreListVO>> getStoreListBySearch(@RequestBody PageQuery query,
                                                          @TokenRequired User user) {
        if (user == null) {
            return ResultGenerator.genFailResult("USER NOT FOUND");
        }
        List<Store> storeList = storeMapper.findStoreListBySearch(query);
        if (storeList != null) {
            List<StoreListVO> storeListVOList = new ArrayList<>();
            for (Store i : storeList) {
                StoreListVO t = new StoreListVO();
                BeanUtils.copyProperties(i, t);
                storeListVOList.add(t);
            }
            return ResultGenerator.genSuccessResult(storeListVOList);
        }
        return ResultGenerator.genFailResult("STORE NOT FOUND");
    }

    @GetMapping("/store/list")
    @Operation(summary = "查看店铺列表", description = "")
    public Result<List<StoreListVO>> getAllStores(@TokenRequired User user) {
        if (user == null) {
            return ResultGenerator.genFailResult("USER NOT FOUND");
        }
        List<Store> storeList = storeMapper.findAllStores();
        if (storeList != null) {
            List<StoreListVO> storeListVOList = new ArrayList<>();
            for (Store i : storeList) {
                StoreListVO t = new StoreListVO();
                BeanUtils.copyProperties(i, t);
                storeListVOList.add(t);
            }
            return ResultGenerator.genSuccessResult(storeListVOList);
        }
        return ResultGenerator.genFailResult("STORE NOT FOUND");
    }
}
