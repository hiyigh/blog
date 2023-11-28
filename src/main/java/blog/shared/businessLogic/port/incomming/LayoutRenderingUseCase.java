package blog.shared.businessLogic.port.incomming;

import org.springframework.ui.Model;

public interface LayoutRenderingUseCase {
    void AddLayoutTo(Model model);
}
