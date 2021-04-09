package com.exam.service;


import com.exam.model.service.MenuServiceModel;
import com.exam.view.MenuViewModel;

import java.util.List;

public interface MenuService {

    void createMenu(MenuServiceModel menuServiceModel);

    List<MenuViewModel> getAllMenus();
}
