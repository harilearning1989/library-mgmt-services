package com.lib.mgmt;

import com.lib.mgmt.dtos.StudentDTO;
import com.lib.mgmt.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class LibraryManagementApplication implements CommandLineRunner {

	//http://localhost:8081/swagger-ui/index.html
	//https://mkyong.com/spring-boot/spring-rest-validation-example/
	//spring-security/spring-boot-security-jwt-auth-api/
	//https://github.com/roytuts/spring-security/tree/master/spring-boot-security-jwt-auth-api
	//https://github.com/roytuts/angular/tree/master/angular-spring-boot-jwt-auth
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(LibraryManagementApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

	/*private StudentService studentService;

	@Autowired
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}*/

	//private BookService bookService;

	//@Autowired
	//public void setBookService(BookService bookService) {
	//	this.bookService = bookService;
	//}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("On Loading spring boot execute");
		saveStudentDetails();
		//saveBookDetail();
	}

	/*private void saveBookDetail() {
		long count = bookService.countBooks();
		if(count <= 5){
			System.out.println(count);
			bookService.saveAllBooks();
		}
	}*/

	private void saveStudentDetails() {
		/*long count = studentService.countStudents();
		if(count <= 5){
			List<StudentDTO> studentDTOS = studentService.readJson();
			System.out.println(studentDTOS.size());
			studentService.saveAll(studentDTOS);
		}*/
	}

}
