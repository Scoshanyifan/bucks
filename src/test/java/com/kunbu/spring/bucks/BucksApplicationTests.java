package com.kunbu.spring.bucks;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.kunbu.spring.bucks.common.dto.CategoryDTO;
import com.kunbu.spring.bucks.common.entity.mongo.RequestLog;
import com.kunbu.spring.bucks.common.param.mongo.RequestLogQueryParam;
import com.kunbu.spring.bucks.dao.mongodb.LogMongoDB;
import com.kunbu.spring.bucks.dao.redis.RedisManager;
import com.kunbu.spring.bucks.utils.IDGenerateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BucksApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(BucksApplicationTests.class);

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private LogMongoDB logMongoDB;

    @Test
    public void testRedis() {

        boolean setValue = redisManager.set("name", "kunbu");
        String strResult = redisManager.getString("name");
        logger.info(">>> setValue:{}, strResult:{}, objResult:{}", setValue, strResult);
        boolean incrValue = redisManager.setnx("incrNum", 2L);
        long incrResult = redisManager.incr("incrNum");
        logger.info(">>> incrValue:{}, incrResult:{}", incrValue, incrResult);
    }

    @Test
    public void testMongo() {

        RequestLog log = new RequestLog();
        log.setDescription("mongodb");
        log.setCreateTime(new Date());
        RequestLog result = logMongoDB.saveRequestLog(log);
        logger.info(">>> log id:{}", result.getId());

        RequestLogQueryParam param = new RequestLogQueryParam();
        long nowTime = System.currentTimeMillis();
        // 时区问题，spring帮我们做了转换
        param.setStartTime(new Date(nowTime - 1000L * 60 * 10));
        param.setEndTime(new Date());
        PageInfo pageInfo = logMongoDB.listRequestLog(param);
        logger.info(pageInfo.toString());
    }

    public static void main(String[] args) {
        CategoryDTO parse = getCategoryTree();

        Set<String> cateNameSet = Sets.newHashSet();
        Set<Integer> cateCodeSet = Sets.newHashSet();
        List<CategoryDTO> cateDTOList = Lists.newArrayList();
        String checkResult = checkCategoryTree(parse, cateNameSet, cateCodeSet, cateDTOList, null, 1);
        System.out.println("checkResult: " + checkResult);
    }

    private static CategoryDTO getCategoryTree() {
        CategoryDTO l1 = newCategoryDTO(1);
        List<CategoryDTO> l2List = Lists.newArrayList();
        l1.setSubs(l2List);
        System.out.println("l1:" + l1);
        for (int i = 0; i < 3; i++) {
            CategoryDTO l2 = newCategoryDTO(2);
            l2List.add(l2);
            List<CategoryDTO> l3List = Lists.newArrayList();
            l2.setSubs(l3List);
            for (int j = 0; j < 2; j++) {
                CategoryDTO l3 = newCategoryDTO(3);
                l3List.add(l3);
            }
        }
        System.out.println(l1);
        String jsonStr = JSONObject.toJSONString(l1);
        System.out.println("jsonstr:" + jsonStr);
        CategoryDTO parse = JSONObject.parseObject(jsonStr, CategoryDTO.class);
        System.out.println(parse);
        return parse;
    }

    private static CategoryDTO newCategoryDTO(int level) {
        CategoryDTO saveDTO = new CategoryDTO();
        saveDTO.setCategoryCode(new Random().nextInt(1000));
        saveDTO.setCategoryName("衣服" + new Random().nextInt(1000));
        saveDTO.setLevel(level);
        return saveDTO;
    }

    private static String checkCategoryTree(
            CategoryDTO dto,
            Set<String> cateNameSet,
            Set<Integer> cateCodeSet,
            List<CategoryDTO> cateDTOList,
            String parentId,
            int level) {
        System.out.println("parentId:" + parentId + ", level:" + level);
        System.out.println("cateNameSet:" + cateNameSet);
        System.out.println("cateCodeSet:" + cateCodeSet);
        System.out.println("dto:" + dto);
        cateDTOList.add(dto);

        boolean checkName = cateNameSet.add(dto.getCategoryName());
        System.out.println("checkName:" + checkName);
        boolean checkCode = cateCodeSet.add(dto.getCategoryCode());
        System.out.println("checkCode:" + checkCode);
        if (!checkName) {
            return dto.getCategoryName();
        }
        if (!checkCode) {
            return dto.getCategoryCode().toString();
        }
        if (dto.getLevel() != level) {
            return "level";
        }
        level++;
        //填入id
        String categoryId = IDGenerateUtil.DBUniqueID();
        dto.setCategoryId(categoryId);
        dto.setParentId(parentId);

        String checkResult = null;
        List<CategoryDTO> subs = dto.getSubs();
        if (CollectionUtils.isNotEmpty(subs)) {
            for (CategoryDTO sub : subs) {
                checkResult = checkCategoryTree(sub, cateNameSet, cateCodeSet, cateDTOList, categoryId, level);
                if (checkResult != null) {
                    break;
                }
            }
        }
        return checkResult;
    }
}
