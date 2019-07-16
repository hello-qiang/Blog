package com.example.blog.controller;

import com.example.blog.entity.Article;
import com.example.blog.entity.Category;
import com.example.blog.entity.User;
import com.example.blog.service.ArticleService;
import com.example.blog.service.CategoryService;
import com.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author :qiang
 * @date :2019/7/11 下午9:28
 * @description :用户信息控制类
 * @other :
 */
@RequestMapping(value = "/admin")
@Controller
public class UserController {


    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;

    @Autowired
    CategoryService categoryService;


    /**
     * 默认进入后台主页
     *
     * @return
     */
    @RequestMapping(value = "")
    public String admin(Model model) {
        //查询所有的博客信息在页面进行显示
        List<Article> list = articleService.selectAll();
        model.addAttribute("articles", list);
        return "admin/index";
    }

    /**
     * 登陆模块
     *
     * @return
     */
    @RequestMapping(value = "/login")
    public String login() {
        return "admin/login";
    }

    /**
     * 登陆验证模块，验证成功后跳转至后台主页,否则跳转到登陆界面
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/dologin", method = RequestMethod.POST)
    public String doLogin(User user) {
        //如果可以获取到用户名和密码则成功否则失败
        if (userService.getUSer(user.getUserName(), user.getUserPassword())) {
            return "redirect:/admin";
        } else {
            return "admin/login";
        }
    }

    /**
     * 进入写博客界面
     *
     * @return
     */
    @RequestMapping(value = "/write")
    public String write(Model model) {

        //查询所有的类别信息传送到前端
        List<Category> categories = categoryService.selectAll();
        model.addAttribute("categories", categories);
        model.addAttribute("article", new Article());

        return "admin/write";
    }


    /**
     * 删除博客模块
     *
     * @param aId：接受前端返回的博客id
     * @return ：重定向到index页面
     */
    @RequestMapping(value = "/delete")
    public String deleteBlog(@RequestParam("aId") String aId) {

        articleService.deleteBlog(aId);
        return "redirect:/admin";
    }


    @RequestMapping(value = "/update")
    public String updateBlog(@RequestParam("aId") String aId, Model model) {
        //根据id查询博客@RequestParam("id") String id,Model model
        Article article = articleService.selectById(aId);

        model.addAttribute("article", article);
        Category category = article.getCategory();
        model.addAttribute("category1", category);
        return "admin/update";

    }

    /**
     * 保存博客模块
     *
     * @param article
     * @return
     */
    @RequestMapping(value = "/save")
    public String save(Article article) {
        //保存博客信息
        articleService.saveBlog(article);

        return "redirect:";
    }

}

