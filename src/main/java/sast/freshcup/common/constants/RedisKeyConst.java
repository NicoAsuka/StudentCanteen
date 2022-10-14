package sast.freshcup.common.constants;

import sast.freshcup.entity.Account;

/**
 * @author: 風楪fy
 * @create: 2022-01-20 01:19
 **/
public class RedisKeyConst {
    public static String getTokenKey(Account account) {
        return "TOKEN:" + account.getUsername();
    }

    public static String getAnswerKey(Long contestId, Long uid, Long problemId) {
        return contestId + "-ANSWER:" + uid + ":" + problemId;
    }
}
