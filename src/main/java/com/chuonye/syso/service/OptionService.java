package com.chuonye.syso.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chuonye.syso.SiteProperties;
import com.chuonye.syso.domain.entity.Option;
import com.chuonye.syso.repository.OptionRepository;

/**
 * 系统配置基本操作
 * 
 * @author chuonye@foxmail.com
 */
@Service
public class OptionService {
    public static Option empty = new Option();
    public static final String CACHE_OPTIONS = "cache.options";

    @Autowired
    private OptionRepository optionDao;

    @Transactional
    @CacheEvict(value = CACHE_OPTIONS, allEntries = true, beforeInvocation = true)
    public void addOptions(Map<String, String> options) {
        List<Option> optList = new ArrayList<>(options.size());

        for (Entry<String, String> entry : options.entrySet()) {
            Option opt = new Option();
            opt.setName(entry.getKey());
            opt.setValue(entry.getValue());
            
            optList.add(opt);
        }

        optionDao.saveAll(optList);
    }

    @Cacheable(value = CACHE_OPTIONS, key = "'all-options'")
    public Map<String, String> getAllOptions() {
        SiteProperties[] props = SiteProperties.values();

        Map<String, String> opts = new HashMap<String, String>(props.length);

        for (SiteProperties prop : props) {
            opts.put(prop.key(), optionDao.findById(prop.key()).orElse(empty).getValue());
        }

        return opts;
    }

    public String getOption(String name) {
        return getAllOptions().get(name);
    }
}
