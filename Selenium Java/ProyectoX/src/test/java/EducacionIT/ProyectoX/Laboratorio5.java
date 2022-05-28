package EducacionIT.ProyectoX;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Utilities.CapturaEvidencia;

public class Laboratorio5 {
	String url = "http://automationpractice.com/";
	String driverPath = "..\\ProyectoX\\Driver\\chromedriver.exe";
	String screenShotPath = "..\\ProyectoX\\Evidencia\\";
	String docPath = "..\\ProyectoX\\Evidencia\\documentoEvidencia.docx";
	File screen;
	WebDriver driver;
	
	@BeforeSuite
	public void setUp() {
		// Acciones que se van a ejecutar antes de toda la suite de pruebas
		System.out.println("BeforeSuite - Inicio de la suite de pruebas");
	}
	
	@BeforeClass
	public void beforeClasss() {
		// Acciones que se ven a ejecutar antes de cualquier @Test de este archivo
		System.out.println("BeforeClass - Inicio de la clase java");
		
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
		driver.get(url);
	}
	
	@BeforeTest
	public void beforeTest() {
		// Acciones que se van a ejecutar antes de cada @Test
		System.out.println("BeforeTest - Inicio de la Prueba @Test");
	}
	
	@Test(description="CP01 - Buscar Palabra en AutomationPractica", priority=20)
	public void buscarPalabra() throws IOException {
		
		//Captura de evidencia
		
		screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screen, new File(screenShotPath + "01_Pantalla_Inicial.jpg"));
		
		// Pasos de la prueba que queremos hacer
		WebElement txtBuscador = driver.findElement(By.id("search_query_top"));
		txtBuscador.sendKeys("skirt");
		
		//Captura de evidencia
		
		screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screen, new File(screenShotPath + "02_Palabra_a_buscar.jpg"));
		
		txtBuscador.sendKeys(Keys.ENTER);
		
		//Captura de evidencia
		
		screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screen, new File(screenShotPath + "03_Resultado_de_la_Busqueda.jpg"));
		
		// Comprobar que luego de hacer la busqueda el titulo sea "Search - My Store"
		// y que la URL sea http://automationpractice.com/index.php?controller=search&orderby=position&orderway=desc&search_query=jeans&submit_search=
		Assert.assertEquals(driver.getTitle(), "Search - My Store");
		Assert.assertEquals(driver.getCurrentUrl(), "http://automationpractice.com/index.php?controller=search&orderby=position&orderway=desc&search_query=skirt&submit_search=");
	}
	
	@Test(description="CP02 - Visitar la sección de ContactUs de AutomationPractice", priority=10)
	public void irAContactUs() throws InvalidFormatException, IOException, InterruptedException {
		
		CapturaEvidencia.escribirTituloEnDocumento(docPath, "Documento De Evidencia de Pruebas", 18);
		
		CapturaEvidencia.capturarPantallaEnDocumento(driver, screenShotPath + "img.jpg", docPath, "Pagina Home");
		
		driver.findElement(By.linkText("Contact us")).click();
		
		CapturaEvidencia.capturarPantallaEnDocumento(driver, screenShotPath + "img.jpg", docPath, "Pagina Contact us");
		
		Select subject = new Select(driver.findElement(By.id("id_contact")));
		subject.selectByVisibleText("Customer service");
		
		driver.findElement(By.name("from")).sendKeys("correo@correo.com");
		
		driver.findElement(By.cssSelector("#id_order")).sendKeys("1");
		
		driver.findElement(By.xpath("//input[@id='fileUpload']")).sendKeys("C:\\addIntengerData.txt");
		
		driver.findElement(By.tagName("textarea")).sendKeys("Mensaje de felicitación");
		
		CapturaEvidencia.capturarPantallaEnDocumento(driver, screenShotPath + "img.jpg", docPath, "Datos De Entrada");
		
		driver.findElement(By.name("submitMessage")).click();
		
		CapturaEvidencia.capturarPantallaEnDocumento(driver, screenShotPath + "img.jpg", docPath, "Resultado del envio Contact us");
	}
	
	@AfterTest
	public void afterTest() {
		System.out.println("AfterTest - Fin de la pruebas @Test");
	}
	
	@AfterClass
	public void afterClass() {
		System.out.println("AfterClass - Fin de la Clase Java");
		
		//driver.close();
	}
	
	@AfterSuite
	public void afterSuite() {
		System.out.println("AfterSuite - Fin de la Suite de Pruebas");
	}
}
