package in.ashokit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.ashokit.dto.CounsellorDTO;
import in.ashokit.dto.DashboardResponseDTO;
import in.ashokit.service.CounsellorService;
import in.ashokit.service.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {

	@Autowired
	private CounsellorService counsellorService;

	@Autowired
	private EnquiryService enqService;

	@GetMapping("/")
	public String index(Model model) {

		CounsellorDTO cdto = new CounsellorDTO();
		model.addAttribute("counsellor", cdto);

		return "index";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest req, Model model) {

		HttpSession session = req.getSession(false);
		session.invalidate();

		CounsellorDTO cdto = new CounsellorDTO();
		model.addAttribute("counsellor", cdto);

		return "index";
	}

	@PostMapping("/login")
	public String handleLogin(HttpServletRequest req, CounsellorDTO counsellor, Model model) {

		CounsellorDTO counsellorDTO = counsellorService.login(counsellor);

		if (counsellorDTO == null) {

			CounsellorDTO cdto = new CounsellorDTO();
			model.addAttribute("counsellor", cdto);

			model.addAttribute("emsg", "Invalid Credentials");
			return "index";

		} else {
			Integer counsellorId = counsellorDTO.getCounsellorId();

			// store counsellorId in http session obj
			HttpSession session = req.getSession(true);
			session.setAttribute("counsellorId", counsellorId);

			DashboardResponseDTO dashboardDto = enqService.getDashboardInfo(counsellorId);

			model.addAttribute("dashboardDto", dashboardDto);

			return "dashboard";
		}
	}

	@GetMapping("/register")
	public String registerPage(Model model) {

		CounsellorDTO cdto = new CounsellorDTO();
		model.addAttribute("counsellor", cdto);

		return "register";
	}

	@PostMapping("/register")
	public String handleRegister(@ModelAttribute("counsellor") CounsellorDTO counsellor, Model model) {
		boolean unique = counsellorService.uniqueEmailCheck(counsellor.getEmail());
		if (unique) {
			boolean register = counsellorService.register(counsellor);
			if (register) {
				model.addAttribute("smsg", "Registration Success");
			} else {
				model.addAttribute("emsg", "Registration Failed");
			}
		} else {
			model.addAttribute("emsg", "Enter Unique Email");
		}
		return "register";
	}

	@GetMapping("/dashboard")
	public String displayDashboard(HttpServletRequest req, Model model) {

		HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");

		DashboardResponseDTO dashboardDto = enqService.getDashboardInfo(counsellorId);

		model.addAttribute("dashboardDto", dashboardDto);

		return "dashboard";

	}

}
