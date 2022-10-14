package sast.freshcup.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import sast.freshcup.common.enums.ErrorEnum;
import sast.freshcup.entity.Account;
import sast.freshcup.exception.LocalRunTimeException;
import sast.freshcup.mapper.AccountMapper;
import sast.freshcup.service.ImportService;
import sast.freshcup.util.RandomUtil;

import java.io.UnsupportedEncodingException;
import java.util.List;

// 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
@Slf4j
@Component
public class JugerDataListener implements ReadListener<Account> {

    @Autowired
    private ImportService importService;

    @Autowired
    AccountMapper accountMapper;

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;

    /**
     * 缓存的数据
     */
    private List<Account> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param importService
     */
    public JugerDataListener(ImportService importService) {
        this.importService = importService;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(Account data, AnalysisContext context) {
        //这里无法返回resultMap
//        Map resultMap = new HashMap();
//        AtomicInteger success = new AtomicInteger();
//        AtomicInteger failure = new AtomicInteger();

        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        String username = data.getUsername();
        List<Account> DBusername = accountMapper.selectList(new QueryWrapper<Account>().eq("username", username));
        if (!DBusername.isEmpty()){
//            failure.incrementAndGet();
            throw new LocalRunTimeException(ErrorEnum.USER_EXIST);
        }

        String md5Password;
        try {
            md5Password = DigestUtils.md5DigestAsHex((data.getUsername() + RandomUtil.randomStr())
                    .getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        data.setPassword(md5Password);
        data.setRole(3);
//        success.getAndIncrement();
        cachedDataList.add(data);

        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }

//        resultMap.put("success",success);
//        resultMap.put("failure",failure);
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        importService.saveBatch(cachedDataList);
        log.info("存储数据库成功！");
    }
}