package com.spark.bitrade.controller;

import com.spark.bitrade.constant.PageModel;
import com.spark.bitrade.entity.Coin;
import com.spark.bitrade.service.CoinService;
import com.spark.bitrade.util.MessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("coin")
public class CoinController extends BaseController {
    @Autowired
    private CoinService coinService;

    @GetMapping("legal")
    public MessageResult legal() {
        List<Coin> legalAll = coinService.findLegalAll();
        return success(legalAll);
    }

    @GetMapping("legal/page")
    public MessageResult findLegalCoinPage(PageModel pageModel) {
        Page all = coinService.findLegalCoinPage(pageModel);
        return success(all);
    }

    @RequestMapping("supported")
    public List<Map<String,String>>  findCoins(){
        List<Coin> coins = coinService.findAll();
        List<Map<String,String>> result = new ArrayList<>();
        coins.forEach(coin->{
            if(coin.getHasLegal() == Boolean.FALSE) {
                Map<String, String> map = new HashMap<>();
                map.put("name",coin.getName());
                map.put("nameCn",coin.getNameCn());
                map.put("withdrawFee",String.valueOf(coin.getMinTxFee()));
                map.put("enableRecharge",String.valueOf(coin.getCanRecharge().getOrdinal()));
                map.put("minWithdrawAmount",String.valueOf(coin.getMinWithdrawAmount()));
                map.put("enableWithdraw",String.valueOf(coin.getCanWithdraw().getOrdinal()));
                map.put("maxWithdrawAmount",coin.getMaxWithdrawAmount().toPlainString());
                map.put("withdrawThreshold",coin.getWithdrawThreshold().toPlainString());
                result.add(map);
            }
        });
        return result;
    }
}
