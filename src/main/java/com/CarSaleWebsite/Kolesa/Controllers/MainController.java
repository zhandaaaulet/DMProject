package com.CarSaleWebsite.Kolesa.Controllers;

import com.CarSaleWebsite.Kolesa.Functions.IQRCGenerator;
import com.CarSaleWebsite.Kolesa.Functions.StringConfigurerFunctions;
import com.CarSaleWebsite.Kolesa.Models.Food;
import com.CarSaleWebsite.Kolesa.Models.Usr;
import com.CarSaleWebsite.Kolesa.Repositories.FoodRepository;
import com.CarSaleWebsite.Kolesa.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
public class MainController {
    @Autowired
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final FoodRepository foodRepository;

    public MainController(UsersRepository usersRepository, PasswordEncoder passwordEncoder, FoodRepository foodRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.foodRepository = foodRepository;
    }

    @GetMapping("/")
    public String MainPage(Model model) {
        Iterable<Food> foods=foodRepository.findAll();
        model.addAttribute("foods",foods);
        return "main-page";
    }

    @GetMapping("/login")
    public String LoginPage() {

        return "login-page";
    }

    @GetMapping("/users")
    public String users(Model model) {
        Iterable<Usr> users = usersRepository.findAll();
        model.addAttribute("users", users);
        return "users-page";
    }

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {
        model.addAttribute("authuser", principal.getName());
        return "profile-page";
    }

    @GetMapping("/users/add")
    public String addUserPage() {
        return "add-user";
    }

    @PostMapping("/users/add")
    public String addUserAction(@RequestParam String txtUsername,
                                @RequestParam String txtPassword,
                                @RequestParam String role,
                                @RequestParam String permission) {
        Usr user= new Usr(txtUsername,passwordEncoder.encode(txtPassword),role,permission);
        usersRepository.save(user);

        return "redirect:/users";
    }
    @GetMapping("/food/add")
    public  String addFood()
    {
        return "add-food";
    }
    @PostMapping("/food/add")
    public String addFoodAction(@RequestParam String category,
                                @RequestParam String nme,
                                @RequestParam String description,
                                @RequestParam long price,
                                @RequestParam String size,
                                @RequestParam String image){
        Food burger=new Food(StringConfigurerFunctions.replaceWhiteSpaceWithMinus(nme),description,price,size,image,category);
        foodRepository.save(burger);
        return "redirect:/";

    }

    @GetMapping("/{category}/{name}")
        public String detailedViewFood(Model model,
                                       @PathVariable(value = "category") String category,
                                       @PathVariable(value = "name")String name){
        if(!foodRepository.existsByCategoryAndName(category,name)){
            return "redirect:/";
        }
        Food food=foodRepository.findFoodByCategoryAndName(category,name);
        model.addAttribute("food",food);

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);

        model.addAttribute("date",strDate);

        return "detailed-view-food";

    }


}


