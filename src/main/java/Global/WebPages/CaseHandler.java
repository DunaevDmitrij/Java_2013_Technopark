package Global.WebPages;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 07.11.13
 * Time: 22:12
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Аннотация для частичных обработчиков запроса
 */
@Retention(RetentionPolicy.RUNTIME)
@interface CaseHandler {
    int routine();                  // Реализуемая подзадача обработки
    RequestType reqType();          // Обработчики разделяются по типу запроса
}