package net.gondr.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import net.gondr.domain.RegisterDTO;
import net.gondr.util.MediaUtil;

public class RegisterValidator implements Validator{
	
	private static final String emailReg = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+\\.[A-Za-z]{2,3}$";
	
	private Pattern pattern;
	
	public RegisterValidator() {
		pattern = Pattern.compile(emailReg);
	}
	
	@Override
	public boolean supports(Class<?> arg0) {
		return RegisterDTO.class.isAssignableFrom(arg0);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userid", "required", "유저 아이디 값은 필수 입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "required", "유저 이름은 필수 입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required", "비밀번호 값은 필수 입니다.");
		
		RegisterDTO dto = (RegisterDTO) target;
		
		if(!dto.getPassword().equals(dto.getPasswordConfirm())) {
			errors.rejectValue("password", "bad", "비밀번호와 확인이 일치하지 않습니다.");
		}
		
		if(!dto.getUserid().isEmpty()) {
			Matcher matcher = pattern.matcher(dto.getUserid());
			if(!matcher.matches()) {
				errors.rejectValue("userid", "bad", "이메일 형식이 올바르지 않습니다.");
			}
		}
		
		MultipartFile img = dto.getProfileImg();
		if(!img.getOriginalFilename().equals("")) {
			String filename = img.getOriginalFilename();
			String ext = filename.substring(filename.lastIndexOf(".")+1);
			if(MediaUtil.getMediaType(ext) == null) {
				errors.rejectValue("profileImg", "bad", "프로필 이미지는 이미지 파일만 업로드 가능합니다.");
			}
		}
	}
}
