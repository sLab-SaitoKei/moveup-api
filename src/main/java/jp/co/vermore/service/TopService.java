package jp.co.vermore.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.vermore.form.TopForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.vermore.entity.Top;
import jp.co.vermore.mapper.TopMapper;

@Service
public class TopService {
    @Autowired
    private TopMapper topMapper;

    public List <Top> getTopPic(){
        List<Top> topList = topMapper.getTopPic();
        return topList;
    }

    public List <Top> getTopForAdmin(){
        List<Top> topList = topMapper.getTopForAdmin();
        return topList;
    }

    public int updateTop(TopForm topForm){
        int count = 0;
        List<String> urlList = new ArrayList<String>();
//        String url1 = topForm.getPicUrl1() + "----------" + topForm.getVideoUrl1();
//        String url2 = topForm.getPicUrl2() + "----------" + topForm.getVideoUrl2();
//        urlList.add(url1);
//        urlList.add(url2);
        urlList.add(topForm.getPicUrl1());
        if(topForm.getType2() == 1){
            urlList.add(topForm.getPicUrl2());
        }else{
            urlList.add(topForm.getVideoUrl2());
        }
        urlList.add(topForm.getUrl3());
        urlList.add(topForm.getUrl4());
        urlList.add(topForm.getUrl5());

        List<Byte> typeList = new ArrayList<>();
        typeList.add(topForm.getType1());
        typeList.add(topForm.getType2());
        typeList.add((byte) 1);
        typeList.add((byte) 1);
        typeList.add((byte) 1);

        List<Byte> itemList = new ArrayList<Byte>();
        itemList.add(topForm.getItemType1());
        itemList.add((byte)0);
        itemList.add(topForm.getItemType3());
        itemList.add(topForm.getItemType4());
        itemList.add(topForm.getItemType5());

        List<String> linkUrlList = new ArrayList<String>();
        linkUrlList.add(topForm.getLinkUrl1());
        linkUrlList.add(topForm.getLinkUrl2());
        linkUrlList.add(topForm.getLinkUrl3());
        linkUrlList.add(topForm.getLinkUrl4());
        linkUrlList.add(topForm.getLinkUrl5());

        for(long id=1; id<6; id++){
            Top top = new Top();
            top.setId(id);
            top.setUrl(urlList.get((int) id-1));
            top.setItemType(itemList.get((int) id-1));
            top.setType(typeList.get((int) id-1));
            top.setLinkUrl(linkUrlList.get((int) id-1));
            top.setUpdateDatetime(new Date(System.currentTimeMillis()));
            count = topMapper.updateTop(top);
        }
        return count;
    }

}
