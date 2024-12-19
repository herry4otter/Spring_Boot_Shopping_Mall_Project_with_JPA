package com.shop.domain.item.repository;

import com.shop.domain.item.dto.ItemSearchDto;
import com.shop.domain.main.dto.MainItemDto;
import com.shop.domain.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
