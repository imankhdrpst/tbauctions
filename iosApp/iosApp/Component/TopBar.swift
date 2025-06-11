import SwiftUI

struct TopBar: View {
    var title: String?
    
    var body: some View {
        ZStack {
            // Title
            if let title = title {
                Text(title)
                    .lineLimit(1)
                    .padding(.horizontal, 32)
                    .frame(maxWidth: .infinity, alignment: .center)
            }        
        }
        .frame(maxWidth: .infinity)
    }
}
