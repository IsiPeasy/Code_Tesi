package com.space.carssbo.component.coppieLingue;

import com.space.UserException;
import com.space.component.DownloadBehavior;
import com.space.component.generic.DefaultPanel;
import com.space.component.generic.UpperTextField;
import com.space.exporter.ExcelExporterException;
import com.space.exporter.ExcelExporterFactory;
import com.space.exporter.IExcelExporter;
import com.space.model.coppieLingue.CoppiaLingue;
import com.space.printengine.IReportRenderer;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxPreprocessingCallDecorator;
import org.apache.wicket.markup.html.panel.Panel;
import com.space.Context;
import com.space.component.generic.DefaultPanel;
import com.space.component.generic.EnabledLabelBlockingAjaxButton;
import com.space.component.generic.autodatatable.AutoDataTableFactory;
import com.space.model.master2rata.Master2Rata;
import com.space.model.master2rata.TipoMaster;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.util.resource.IResourceStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CoppieLinguePanel extends DefaultPanel<CoppiaLingue> {

    private final Form form;
    private WebMarkupContainer resultsContainer;
    private IModel<CoppiaLingue> selectedModel;
    private IModel<List<CoppiaLingue>> resultModel;
    private boolean searched = false;
    private EnabledLabelBlockingAjaxButton cerca;
    private EnabledLabelBlockingAjaxButton inserisci;
    private EnabledLabelBlockingAjaxButton cancella;
    private EnabledLabelBlockingAjaxButton pulisci;
    private EnabledLabelBlockingAjaxButton exportButton;
    private TextField<Integer> annoField;
    private TextField<String> codField;
    private Buttonbar buttobar;
    private Form<?> buttonBarForm;
    private IModel<List<CoppiaLingue>> listRisultati;

    public CoppieLinguePanel(String id, IModel<CoppiaLingue> model, Form form) {
        super(id, model);
        this.form = form;
        selectedModel = new Model<CoppiaLingue>();
        resultModel = new ListModel<>();
        if (model.getObject().getCscCod() != null) {
            resultModel.setObject(Context.get().getCoppieLingueDao().search(getModelObject()));
            searched = true;
        }
        if (model.getObject().getAnnoacc() == null) {
            model.getObject().setAnnoacc(Context.get().getCoppieLingueDao().loadAnnoAcc());
        }
    }

    @Override
    protected void onInitialize() {
        setOutputMarkupId(true);
        super.onInitialize();
        FeedbackPanel infoFeedback = new FeedbackPanel("infoFeedback");
        infoFeedback.setFilter(new IFeedbackMessageFilter() {
            @Override
            public boolean accept(FeedbackMessage message) {
                return message.getLevel() == FeedbackMessage.INFO;
            }
        });
        add(infoFeedback);
        add(codField = new UpperTextField("cdcField", //problema del cdcfield non corretto
                new PropertyModel<>(getModel(), "cscCod"), String.class));
        codField.setRequired(false);
        add(annoField = new TextField<Integer>("annoField",
                new PropertyModel<>(getModel(), "annoacc"), Integer.class));
        annoField.setRequired(true);
        AutoDataTableFactory<CoppiaLingue> myTableFactory = new AutoDataTableFactory<CoppiaLingue>(this) {

            @Override
            public int getNumberOfItemsPerPage() {
                return 6;
            }

            @Override
            protected void onSelect(Item item, AjaxRequestTarget target) {
                super.onSelect(item, target);
                target.add(buttobar);
            }
        };
        List<String> propertyToShow = new ArrayList<String>();
        propertyToShow.add("annoacc");
        propertyToShow.add("cscCod");
        propertyToShow.add("codLingua1");
        propertyToShow.add("desLingua1");
        propertyToShow.add("codLingua2");
        propertyToShow.add("desLingua2");


        resultsContainer = new WebMarkupContainer("results") {
            @Override
            public boolean isVisible() {
                return searched;
            }
        };
        //resultsContainer.setVisible(false);
        resultsContainer.addOrReplace(myTableFactory.CreateSelectableDataTable(
                "table",
                selectedModel,
                resultModel,
                propertyToShow
        ));
        buttobar = new Buttonbar("buttonbar") {
            @Override
            protected void onBeforeRender() {
                super.onBeforeRender();
                //modifica.mySetEnabled(searched && selectedModel.getObject()!=null);
                cancella.mySetEnabled(searched && selectedModel.getObject() != null);
            }
        };
        add(resultsContainer);
        buttobar.setOutputMarkupId(true);
        cerca = new EnabledLabelBlockingAjaxButton("cerca", form, "mybody", Model.of("cerca")) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                resultModel.setObject(Context.get().getCoppieLingueDao().search(getModelObject()));
                searched = true;
                target.add(CoppieLinguePanel.this);
                target.add(buttobar);
            }
        };

        inserisci = new EnabledLabelBlockingAjaxButton("inserisci", form, "mybody", Model.of("inserisci")) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                form.addOrReplace(new CoppieLingueDetailPanel("view",
                        Model.of(new CoppiaLingue()),
                        form,
                        CoppieLinguePanel.this,
                        getModel()) {
                    @Override
                    protected void openFeedbackDialog(AjaxRequestTarget target) {
                        CoppieLinguePanel.this.openFeedbackDialog(target);
                    }

                    @Override
                    protected Form getForm() {
                        return CoppieLinguePanel.this.form;
                    }
                });
                target.add(form);
            }
        };
        inserisci.setDefaultFormProcessing(false);
        cancella = new EnabledLabelBlockingAjaxButton("cancella", form, "mybody", Model.of("elimina")) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                Context.get().getCoppieLingueDao().delete(selectedModel.getObject());
                resultModel.setObject(Context.get().getCoppieLingueDao().search(getModelObject()));
                selectedModel.setObject(null);
                searched = true;
                CoppieLinguePanel.this.info("Record eliminato con successo");
                target.add(CoppieLinguePanel.this);
                target.add(buttobar);
            }

            @Override
            protected IAjaxCallDecorator getDecorationToInnerButton(IAjaxCallDecorator superDecorator) {

                return new AjaxPreprocessingCallDecorator(superDecorator) {
                    @Override
                    public CharSequence preDecorateScript(CharSequence script) {
                        return "if (!confirm('Confermi la cancellazione dell elemento selezionato?')) {Mask.hide('"
                                + getBodyMarkupId() +
                                "'); return false;} else " + script;
                    }
                };
            }
        };


        pulisci = new EnabledLabelBlockingAjaxButton("pulisci", form, "mybody", Model.of("pulisci")) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                resultModel.setObject(new ArrayList<>());
                searched = false;
                annoField.clearInput();
                codField.clearInput();
                setModelObject(new CoppiaLingue());
                getModelObject().setAnnoacc(Context.get().getCoppieLingueDao().loadAnnoAcc());
                target.add(CoppieLinguePanel.this);
                target.add(buttobar);
            }
        };
        pulisci.setDefaultFormProcessing(false);

        final DownloadBehavior exportBehavior = new DownloadBehavior() {

            @Override
            protected void onBeforeDownload() {

            }

            @Override
            protected void onPrint() {
                extractFlussi();
            }

        };

        exportButton = new EnabledLabelBlockingAjaxButton("export", buttonBarForm, "mybody", Model.of("esporta")) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

                onExport(target);
                exportBehavior.execute(target);
            }
        };
        exportButton.mySetEnabled(true);
        exportButton.add(exportBehavior);
        exportButton.setDefaultFormProcessing(false);

        buttobar.add(cerca);
        // buttobar.add(modifica);
        buttobar.add(cancella);
        buttobar.add(inserisci);
        buttobar.add(pulisci);
        buttobar.add(exportButton);
        form.addOrReplace(buttobar);
    }

    protected abstract void onExport(AjaxRequestTarget target);

    protected abstract void openFeedbackDialog(AjaxRequestTarget target);

    private void extractFlussi() {

        try {

            // renderizzo il report excel passando dalla lists
            String headers[] = {
                    "annoacc",
                    "cscCod",
                    "codLingua1",
                    "desLingua1",
                    "codLingua2",
                    "desLingua2"
            };


            IExcelExporter renderer = ExcelExporterFactory.excelRendererFor(Context.get().getCoppieLingueDao().search(getModelObject()), headers, this);


            //Ritorno lo stream del report renderizzato al client
            IResourceStream excelResourceStream = IReportRenderer.Utils.toResourceStream(renderer.renderToOutputStream());
            ResourceStreamRequestHandler handler = new ResourceStreamRequestHandler(excelResourceStream, "CoppieLingue.xls");
            handler.setContentDisposition(ContentDisposition.ATTACHMENT);
            getRequestCycle().scheduleRequestHandlerAfterCurrent(new ResourceStreamRequestHandler(excelResourceStream, "CoppieLingue.xls"));

        } catch (ExcelExporterException e) {
            throw new UserException(e.getMessage());
        }

    }

    private class Buttonbar extends Panel {

        public Buttonbar(String id) {
            super(id);
        }
    }
}

