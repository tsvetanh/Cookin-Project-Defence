package com.exam.service;


import com.exam.model.service.MenuServiceModel;
import com.exam.view.MenuViewModel;

import java.util.List;

public interface MenuService {

    void createMenu(MenuServiceModel menuServiceModel);

    MenuViewModel findById(Long id);

    List<MenuViewModel> getAllMenus();

    void deleteById(Long id);
}
