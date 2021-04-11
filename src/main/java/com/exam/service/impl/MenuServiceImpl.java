package com.exam.service.impl;

import com.exam.model.entities.Menu;
import com.exam.model.service.MenuServiceModel;
import com.exam.repository.MenuRepository;
import com.exam.repository.UserRepository;
import com.exam.service.MenuService;
import com.exam.view.MenuViewModel;
import com.exam.view.RecipeViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public MenuServiceImpl(MenuRepository menuRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.menuRepository = menuRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createMenu(MenuServiceModel menuServiceModel) {
            Menu menu = modelMapper.map(menuServiceModel, Menu.class);
            menuRepository.save(menu);
    }

    @Override
    public MenuViewModel findById(Long id) {
        return menuRepository
                .findById(id)
                .map(menu -> {
                    MenuViewModel view = modelMapper
                            .map(menu, MenuViewModel.class);
                    return view;

                })
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<MenuViewModel> getAllMenus() {
        List<Menu> menus = menuRepository.findAll();

        List<MenuViewModel> viewModels = new ArrayList<>();

        menus.forEach(menu -> {
            MenuViewModel menuViewModel = modelMapper.map(menu, MenuViewModel.class);
            viewModels.add(menuViewModel);
        });
        return viewModels;
    }

    @Override
    public void deleteById(Long id) {
        menuRepository.deleteById(id);
    }
}
