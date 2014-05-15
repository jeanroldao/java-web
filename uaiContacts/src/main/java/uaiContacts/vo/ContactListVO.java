package uaiContacts.vo;

import java.util.List;

import uaiContacts.model.Contact;

public class ContactListVO {
	
	private String actionMessage;
	private String searchMessage;
	
	private int totalPages;
	private long totalElements;
	private List<Contact> content;

	public ContactListVO() {
		
	}
	
	public ContactListVO(int totalPages, long totalElements,
			List<Contact> content) {
		this.totalPages = totalPages;
		this.totalElements = totalElements;
		this.content = content;
	}
	public String getActionMessage() {
		return actionMessage;
	}
	public void setActionMessage(String actionMessage) {
		this.actionMessage = actionMessage;
	}
	public String getSearchMessage() {
		return searchMessage;
	}
	public void setSearchMessage(String searchMessage) {
		this.searchMessage = searchMessage;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public long getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}
	public List<Contact> getContent() {
		return content;
	}
	public void setContent(List<Contact> content) {
		this.content = content;
	}
}
