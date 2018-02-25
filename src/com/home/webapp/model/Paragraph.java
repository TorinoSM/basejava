package com.home.webapp.model;

public class Paragraph {

    // колонка к которой привязан параграф
    private Column column;
    // номер абзаца в колонке
    private Integer paragraphNumber;
    // тип абзаца
    private ParagraphType paragraphType;

    public Paragraph(Column column) {
        if (column == null) {
            System.out.println("you must specify a valid Column");
            return;
        }
        this.column = column; // связываем абзац с конкретной колонкой
        this.paragraphType = ParagraphType.SIMPLE; // по умолчанию тип абзаца - простой
        Integer paragraphsCount = column.getParagraphsCount(); // берем количество абзацев в колонке
        paragraphNumber = paragraphsCount + 1; // устанавливаем номер абзаца
        column.setParagraphsCount(paragraphsCount + 1); // увеличиваем количество абзацев в колонке
    }

    public Column getColumn() {
        return column;
    }

    public Integer getParagraphNumber() {
        return paragraphNumber;
    }

    public ParagraphType getParagraphType() {
        return paragraphType;
    }
}
