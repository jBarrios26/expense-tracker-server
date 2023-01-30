CREATE OR REPLACE FUNCTION update_category_expenses()
    RETURNS TRIGGER
    LANGUAGE PLPGSQL
AS
$$
BEGIN
    UPDATE budget_categories category
    SET current_spending = current_spending + new.amount
    WHERE category.budget = NEW.budget
      AND category.budget_category = NEW.budget_category;

    RETURN NEW;
END;
$$;


CREATE OR REPLACE TRIGGER UPDATE_CATEGORY_EXPENDING
    AFTER INSERT
    ON budget_expense
    FOR EACH ROW
EXECUTE PROCEDURE update_category_expenses();