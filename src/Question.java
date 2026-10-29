public class Question {
    private String text;
    private String[] options;
    private int correctOption;   // 1 to 4

    public Question(String text, String[] options, int correctOption) {
        this.text = text;
        this.options = options;
        this.correctOption = correctOption;
    }

    public String getText() { return text; }
    public String[] getOptions() { return options; }
    public int getCorrectOption() { return correctOption; }
}