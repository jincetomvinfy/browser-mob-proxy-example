package com.jinceapp.browserproxymob;

import java.io.File;
import java.io.IOException;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

public class BrowserProxyMobRunner {
	public static void main(String[] args) throws IOException {
		BrowserMobProxy proxy = new BrowserMobProxyServer();
		proxy.start();

		Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

		proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);

		ChromeDriverService service = new ChromeDriverService.Builder()
				.usingDriverExecutable(new File("C:/Users/dell/Downloads/chromedriver.exe")).usingAnyFreePort().build();
		ChromeOptions options = new ChromeOptions();
		options.setCapability(CapabilityType.PROXY, seleniumProxy);
		WebDriver driver = new ChromeDriver(service, options);

		proxy.newHar("google.com");
		driver.get("https://www.google.com/");
		Har har = proxy.getHar();
		har.writeTo(new File("C:/Users/dell/Downloads/googlehar.har"));

	}
}
