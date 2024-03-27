//package org.example.aiscore.selenium;
//
//import lombok.extern.log4j.Log4j2;
//import org.example.aiscore.model.h2h.H2H;
//import org.example.aiscore.model.Match;
//import org.example.common.util.ActionUtil;
//import org.jetbrains.annotations.NotNull;
//import org.openqa.selenium.*;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.Wait;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.stereotype.Component;
//
//import java.util.*;
//
//@Component
//@ComponentScan(basePackages = "org.common")
//@Log4j2
//@EnableAsync
//public class ScheduledAiScoreDataImpl implements ScheduledAiScoreData {
//
//	private final ActionUtil actionUtil;
//	private final Wait<WebDriver> wait;
//	private final WebDriver webDriver;
//
//	@Autowired
//	public ScheduledAiScoreDataImpl(ActionUtil actionUtil,
//									@Qualifier("webDriverWait") Wait<WebDriver> wait,
//									@Qualifier("aiscoreWebDriver") WebDriver webDriver) {
//		this.actionUtil = actionUtil;
//		this.wait = wait;
//		this.webDriver = webDriver;
//	}
//
//	public void init() {
//		//	Sort by time
//		//	Switch to schedule tab
//		try {
//			WebElement sortByTimeEle = wait.until(dv -> dv.findElement(By.className("el-checkbox__inner")));
//			sortByTimeEle.click();
//			WebElement changTabBox = wait.until(dv -> dv.findElement(By.className("changTabBox")));
//			List<WebElement> tags = changTabBox.findElements(By.className("changeItem"));
//			tags.get(3).click();
//			Thread.sleep(2000);
//		} catch (Exception ex) {
//			log.error("Error occurred while trying to sort by time and switch to schedule tab", ex);
//		}
//	}
//
//
//	@Override
//	public Map<String, List<H2H>> getH2HFromAiScore() {
//
//		Map<String, List<H2H>> h2hMap = new HashMap<>();
//
//		try {
//			this.init();
//			// List<WebElement> allExistSchedulesElement = getAllExistSchedulesElement();
//			// Set<String> allMatchesWithHrefTag = getAllMatchesWithHrefTag(allExistSchedulesElement);
//
//			Set<String> allMatchesWithHrefTag = new HashSet<>();
//			allMatchesWithHrefTag.add("https://www.aiscore.com/match-ud-tamaraceite-marino/l6kegiwvm0gcv75/h2h");
//			allMatchesWithHrefTag.add("https://www.aiscore.com/match-radnik-surdulica-partizan-belgrade/o07dnimjlm4tmkn/h2h");
//			allMatchesWithHrefTag.add("https://www.aiscore.com/match-metta-lu-riga-siauliai/zrkn6iv2w33bwql/h2h");
//			allMatchesWithHrefTag.add("https://www.aiscore.com/match-slavia-mozyr-volna-pinsk/g6763i60832ho7r/h2h");
//
//			for (String selectLinkOpenInNewTab : allMatchesWithHrefTag) {
////			((JavascriptExecutor) webDriver).executeScript("window.open()");
//				webDriver.switchTo().newWindow(WindowType.TAB);
//				webDriver.get(selectLinkOpenInNewTab);
//				List<H2H> h2HsForMatch = getH2HsForMatch();
//				h2hMap.put(selectLinkOpenInNewTab, h2HsForMatch);
//				webDriver.close();
//				webDriver.switchTo().window(webDriver.getWindowHandles().iterator().next());
//			}
//			return h2hMap;
//
//		} catch (Exception ex) {
//			log.error("Error occurred while trying to get names", ex);
//			return Collections.emptyMap();
//		}
//	}
//
//	private Set<String> getAllMatchesWithHrefTag(List<WebElement> elementList) {
//
//		List<String> hreList = new ArrayList<>();
//
//		for (WebElement element : elementList) {
//
//			WebElement compListContainer;
////			String leaguesName;
//
//			try {
//
//				compListContainer = element.findElement(By.className("comp-list"));
////				String title = handleTitleElement(element.findElement(By.className("title")), null);
//				List<WebElement> childHrefList = compListContainer.findElements(By.tagName("a"));
//				for (WebElement matchElement : childHrefList) {
//					String href = matchElement.getAttribute("href");
//					hreList.add(href);
//				}
//			} catch (Exception ex) {
//				continue;
//			}
//
//		}
//
//		return new HashSet<>(hreList);
//	}
//
//
//	// TODO: This method is not yet complete
//	//  Need to return a list of matches
//	private List<WebElement> getAllExistSchedulesElement() {
//
//		List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("vue-recycle-scroller__item-view")));
//		int intSize;
//
//
//		while (true) {
//
////			 Scroll down to the last hotel name in the list
//			((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView();", elements.get(elements.size() - 1));
//
//			try {
//
//				intSize = elements.size();
//				final int finalIntSize = intSize;
//
//				wait.until(dv -> {
//					final int newSize = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("vue-recycle-scroller__item-view"))).size();
//					return newSize > finalIntSize;
//				});
//
//				elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("vue-recycle-scroller__item-view")));
//
//
//			} catch (Exception ex) {
//				System.out.println(ex.getMessage());
//				break;
//			}
//		}
//
//		return elements;
//	}
//
//	private List<H2H> getH2HsForMatch() {
//
//		List<H2H> h2hList = new ArrayList<>();
//		List<WebElement> contentBox = webDriver.findElements(By.className("contentBox"));
//		WebElement firstContentBox = contentBox.get(0);
//
//		List<WebElement> matchContents = firstContentBox.findElements(By.className("matchContent"));
//		for (WebElement matchContent : matchContents) {
//			List<WebElement> items = matchContent.findElements(By.tagName("li"));
//			for (WebElement item : items) {
//				H2H h2H = processH2H(item);
//				h2hList.add(h2H);
//			}
//		}
//
//		return h2hList;
//	}
//
//	private H2H processH2H(WebElement itemScopeEle) {
//		WebElement data = itemScopeEle.findElement(By.tagName("a"));
//		String time = data.findElement(By.className("time")).getText();
//		String teamBox = data.findElement(By.className("teamBox")).getText();
//		String[] team = teamBox.split("\n");
//
//		List<WebElement> scoreBox = data.findElements(By.className("scoreBox"));
//		String scoreBoxHt = scoreBox.get(0).getText();
//		String scoreBoxFt = scoreBox.get(1).getText();
//
//		String[] scoreBoxHtScore = scoreBoxHt.split("\n");
//		String[] scoreBoxFtScore = scoreBoxFt.split("\n");
//
//		return H2H.builder()
//				.time(time)
//				.home(team[0])
//				.away(team[1])
//				.ht(Arrays.asList(scoreBoxHtScore[0], scoreBoxHtScore[1]))
//				.ft(Arrays.asList(scoreBoxFtScore[0], scoreBoxFtScore[1]))
//				.build();
//	}
//
//
//	private String handleTitleElement(@NotNull WebElement titleElement, Match match) {
//		String title = titleElement.getText();
//		String[] titleArr = title.split("\n");
//		// join
//		return String.join(" ", titleArr);
//	}
//
//	private void handleContentElement(WebElement contentElement, Match match) {
//		String contentText = contentElement.getText();
//		System.out.println(contentText);
//		String[] contentSplit = contentText.split("\n");
//		String time = contentSplit[0];
//		String homeName = contentSplit[2];
//		String away = contentSplit[4];
//		match.setHome(homeName);
//		match.setAway(away);
//		match.setTime(time);
//	}
//}
