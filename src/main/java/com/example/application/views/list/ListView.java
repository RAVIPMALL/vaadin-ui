package com.example.application.views.list;

import com.example.application.data.entity.Contact;
import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Contacts | Vaadin CRM")
@Route(value = "")
public class ListView extends VerticalLayout {

    private Grid<Contact> grid = new Grid<>(Contact.class);
    private TextField filterText = new TextField();

    private ContactForm contactForm;

    private CrmService crmService;

    public ListView(final CrmService crmService) {
        this.crmService = crmService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(getToolbar(),grid, contactForm);
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        contactForm.setContact(null);
        contactForm.setVisible(false);
        removeClassName("editing");
    }

    public void editContact(Contact contact) {
        if (contact == null) {
            closeEditor();
        } else {
            contactForm.setContact(contact);
            contactForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void configureGrid(){
        grid.addClassName("contact-grid");
        grid.setColumns("firstName" , "lastName", "email");
        grid.addColumn( contact -> contact.getStatus().getName()).setHeader("Status");
        grid.addColumn( contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach( col -> col.setAutoWidth(true));
        grid.setMaxHeight(70.0F, Unit.EM);
        grid.asSingleSelect().addValueChangeListener(e -> editContact(e.getValue()));
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Contact());
    }

    private HorizontalLayout getToolbar(){
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener( e -> updateList());
        Button addContactButton = new Button("Add Contact");
        addContactButton.addClickListener(e -> addContact());
        var toolbar = new HorizontalLayout(filterText,addContactButton);
        toolbar.setClassName("contact-list-toolbar");
        return toolbar;
    }

    private void configureForm(){
        contactForm = new ContactForm(crmService.getCompanyList(), crmService.getStatusList());
        contactForm.addSaveListener(this::saveContact);
        contactForm.addDeleteListener(this::deleteContact);
        contactForm.addCloseListener(e -> closeEditor());
    }

    private void updateList(){
        grid.setItems(crmService.getContactList(filterText.getValue()));
    }


    private void saveContact(ContactForm.SaveEvent event) {
        crmService.saveContact(event.getContact());
        updateList();
        closeEditor();
    }

    private void deleteContact(ContactForm.DeleteEvent event) {
        crmService.deleteContact(event.getContact());
        updateList();
        closeEditor();
    }
}
