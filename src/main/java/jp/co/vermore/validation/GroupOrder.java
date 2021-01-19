package jp.co.vermore.validation;

/**
 * Created by xieyoujun on 2018/02/04.
 */
import javax.validation.GroupSequence;

@GroupSequence({GroupOrder1.class, GroupOrder2.class})
public interface GroupOrder {

}
