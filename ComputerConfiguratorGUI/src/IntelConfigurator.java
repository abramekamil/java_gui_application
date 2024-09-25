class IntelConfigurator extends Configurator {
    public IntelConfigurator() {
        super(499.0);
    }

    @Override
    public void addProcessor(int option) {
        double processorPrice = switch (option) {
            case 2 -> {
                configurationSummary.append("Processor: Intel® Celeron® G1620 2.70GHz (+$50)\n");
                yield 50.0;
            }
            case 3 -> {
                configurationSummary.append("Processor: Intel® Celeron® G1630 2.80GHz (+$90)\n");
                yield 90.0;
            }
            case 4 -> {
                configurationSummary.append("Processor: Intel® Celeron® G1820 2.70GHz (+$105)\n");
                yield 105.0;
            }
            case 5 -> {
                configurationSummary.append("Processor: Intel® Celeron® G1830 2.80GHz (+$130)\n");
                yield 130.0;
            }
            default -> {
                configurationSummary.append("Processor: Intel® Celeron® G1610 (Included with Base Package)\n");
                yield 0.0;
            }
        };
        runningTotal += processorPrice;
    }

    public void addMemory(int option) {
        double memoryPrice;
        switch (option) {
            case 2 -> {
                configurationSummary.append("Memory: 6GB Dual Channel DDR3 1600MHz (+$28)\n");
                memoryPrice = 28.0;
            }
            case 3 -> {
                configurationSummary.append("Memory: 8GB Dual Channel DDR3 1600MHz (+$58)\n");
                memoryPrice = 58.0;
            }
            case 4 -> {
                configurationSummary.append("Memory: 12GB Dual Channel DDR3 1600MHz (+$108)\n");
                memoryPrice = 108.0;
            }
            case 5 -> {
                configurationSummary.append("Memory: 16GB Dual Channel DDR3 1600MHz (+$176)\n");
                memoryPrice = 176;
            }
            default -> {
                configurationSummary.append("Memory: 4GB Dual Channel DDR3 1600MHz (Included with Base Package)\n");
                memoryPrice = 0.0;
            }
        }
        runningTotal += memoryPrice;
    }

    @Override
    public void addGraphicsCard(int option) {
        double graphicPrice;
        switch (option) {
            case 2:
                configurationSummary.append("Graphics Card: NVIDIA GeForce G310 512MB DDR3 (+$80)\n");
                graphicPrice = 80.0;
                break;
            case 3:
                configurationSummary.append("Graphics Card: NVIDIA GeForce GT620 1GB DDR3 (+$169)\n");
                graphicPrice = 169.0;
                break;
            case 4:
                configurationSummary.append("Graphics Card: NVIDIA GeForce GT640 1GB GDDR5 (+$490)\n");
                graphicPrice = 490.0;
                break;
            default:
            case 1:
                configurationSummary.append("Integrated 3D Graphics (Included with Base Package)\n");
                graphicPrice = 0.0;
                break;
        }
        runningTotal += graphicPrice;
    }
}